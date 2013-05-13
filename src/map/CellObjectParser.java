package map;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.newdawn.slick.tiled.GroupObject;

import utils.triggers.Trigger;
import entities.enemies.Enemy;
import entities.npcs.NPC;
import entities.objects.Cage;
import entities.objects.Door;
import entities.objects.DoorProjectileTrigger;
import entities.objects.DoorTrigger;
import entities.objects.JumpPlatform;
import entities.objects.LeafTest;
import entities.objects.TeleportReciever;
import entities.objects.TeleportSender;
import entities.objects.TextField;
import entities.objects.watereffects.WaterSurfaceEffect;
import game.config.Config;

/**
 * A class responsible for decoding all the entities represented on the map.<br />
 * Should be initialised with all cell's contents prior to parsing to ensure any dependencies
 * traversing multiple cells is correctly interpreted.<br />
 * Note this object will set itself to null after parsing is complete. Please do not keep 
 * references to any object of this class.
 */
public class CellObjectParser {
	
	private static CellObjectParser inst = null;
	
	/**
	 * Returns the current instance of the parser. If one does not currently exist (e.g. it has
	 * been destroyed by a call to {@code parse()}), a new object is created and returned.
	 */
	static CellObjectParser getInst(){
		return inst == null ? inst = new CellObjectParser() : inst;
	}
	
	private final PriorityQueue<IndexedGroupObject> parseQueue = new PriorityQueue<IndexedGroupObject>();
	
	private final Map<String,TeleportReciever> teleportRecievers = new HashMap<String,TeleportReciever>();
	private final Map<String,Trigger> triggers = new HashMap<String,Trigger>();
	private final Map<String,TextField<?>> textFields = new HashMap<String,TextField<?>>();
	
	/**
	 * Adds a new object to the queue to be parsed. Will not parse this object until a call to {@code parse()} is made.
	 */
	void addParseObject(Cell parent, GroupObject go){
		parseQueue.add(new IndexedGroupObject(parent, go));
	}
	
	/**
	 * Consumes this parser and it's contents, populating the cells references with the objects added previously.
	 */
	void parse(){
		while(!parseQueue.isEmpty()){
			IndexedGroupObject igo = parseQueue.poll();
			parseObject(igo.getCell(), igo.unwrap());
		}
		inst = null;
	}
	
	private void parseObject(Cell cell, GroupObject go) {
		String id = go.name;
		int x = go.x / Config.getTileSize();
		int y = go.y / Config.getTileSize();
		int width  = go.width  / Config.getTileSize();
		int height = go.height / Config.getTileSize();
		String subtype = go.props == null ? null : go.props.getProperty("type");
		
		if(id == null || id.isEmpty()){
			System.out.println("Warning: " + go.type + " object at " + x + "," + y + " found with no id.");
		}
		
		if(go.type.equalsIgnoreCase("enemy")){
			cell.addDefaultMovingEntity(Enemy.getNew(cell, subtype, x,y));
		}else if(go.type.equalsIgnoreCase("npc")){
			NPC npc = NPC.getNew(cell, subtype, x,y);
			String tfstr = go.props.getProperty("text_id");
			if(tfstr != null){
				TextField<?> tf = textFields.get(tfstr);
				if(tf != null){
					npc.setTextField(tf);
				}
			}
			cell.addDefaultMovingEntity(npc);
		}else if(go.type.equalsIgnoreCase("door")){
			Door d = new Door(cell,x,y);
			String ts = go.props.getProperty("triggers");
			if(ts != null){
				for(String tid : ts.split("\\s+")) {
					Trigger trig = triggers.get(tid);
					trig.addTriggerable(d);
					d.addTrigger(trig);
				}
			}
			cell.addStaticEntity(d);
		}else if(go.type.equalsIgnoreCase("doorTrigger")){
			DoorTrigger dt = new DoorTrigger(x,y);
			if(id != null){
				triggers.put(id, dt);
			}
			cell.addStaticEntity(dt);
		}else if(go.type.equalsIgnoreCase("doorProjectileTrigger")){
			DoorProjectileTrigger dpt = new DoorProjectileTrigger(x,y);
			if(id != null){
				triggers.put(id, dpt);
			}
			cell.addStaticEntity(dpt);
		}else if(go.type.equalsIgnoreCase("leafTest")){
			cell.addStaticEntity(new LeafTest(x,y));
		}else if(go.type.equalsIgnoreCase("jumpPlatform")){
			cell.addStaticEntity(new JumpPlatform(x,y,width));
		}else if(go.type.equalsIgnoreCase("cage")){
			cell.addDefaultDestructibleEntity(new Cage(cell, x,y,width,height));
		}else if(go.type.equalsIgnoreCase("textField")){
			TextField<?> tf = TextField.newTextField(x,y,width,height,go.props);
			if(id != null){
				textFields.put(id, tf);
			}
			cell.addStaticEntity(tf);
		}else if(go.type.equalsIgnoreCase("teleport_recieve")){
			TeleportReciever tr = new TeleportReciever(cell, x, y, width, height);
			teleportRecievers.put(id,tr);
			cell.addStaticEntity(tr);
		}else if(go.type.equalsIgnoreCase("teleport_send")){
			String dest = go.props.getProperty("dest");
			cell.addStaticEntity(new TeleportSender(teleportRecievers.get(dest), x, y, width, height));
		}else if(go.type.equalsIgnoreCase("waterSurfaceEffect")){
			cell.addStaticEntity(new WaterSurfaceEffect(x, y, width));
		}
	}
	
	/**
	 * A utility class for holding triples of parent cell, parse object and queue priority.
	 */
	private static class IndexedGroupObject implements Comparable<IndexedGroupObject> {
		
		private final Cell c;
		private final int index;
		private final GroupObject go;

		public IndexedGroupObject(Cell c, GroupObject go, int index) {
			this.c = c;
			this.go = go;
			this.index = index;
		}
		
		public IndexedGroupObject(Cell c, GroupObject go){
			this(c,go,ParseOrder.getIndex(go.type));
		}
		
		public GroupObject unwrap(){
			return go;
		}
		
		public Cell getCell(){
			return c;
		}
		
		@Override
		public int compareTo(IndexedGroupObject o) {
			return index - o.index;
		}
		
	}
	
	/**
	 * A utility enum for determining parse ordering.<br />
	 * Objects with no dependencies should be located at a lower ordinal than those 
	 * with a high number of dependencies.
	 */
	private static enum ParseOrder {
		LEAFTEST,
		JUMPPLATFORM,
		ENEMY,
		DOORTRIGGER,
		WATERSURFACEEFFECT,
		DOORPROJECTILETRIGGER,
		CAGE,
		TEXTFIELD,
		TELEPORT_RECIEVE,
		TELEPORT_SEND,
		DOOR,
		NPC;
		
		/**
		 * Returns the priority index of the type specified.
		 * @throws IllegalArgumentException if no such type exists.
		 */
		public static int getIndex(String type) {
			return valueOf(type.toUpperCase()).ordinal();
		}
	}
}
