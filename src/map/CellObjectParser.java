package map;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

import notify.Notification;

import org.newdawn.slick.tiled.GroupObject;

import utils.triggers.CompositeTrigger;
import utils.triggers.TriggerSource;
import utils.triggers.VoidAugmentedTriggerEffect;
import entities.DestructibleEntity;
import entities.Entity;
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
import entities.objects.items.StickItem;
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
	
	private static final Map<String,CompositeTrigger> triggerReference = new HashMap<String,CompositeTrigger>();
	
	public static boolean containsTrigger(CompositeTrigger t){
		return getTrigger(t.getId()) == t;
	}
	
	public static CompositeTrigger getTrigger(String id){
		return triggerReference.get(id);
	}
	
	public static CompositeTrigger removeTrigger(String id){
		return triggerReference.remove(id);
	}
	
	public static CompositeTrigger removeTrigger(CompositeTrigger t){
		return removeTrigger(t.getId());
	}
	
	private final PriorityQueue<IndexedGroupObject> parseQueue = new PriorityQueue<IndexedGroupObject>();
	
	private final Map<String,WeakReference<Entity>> entityReference = new HashMap<String,WeakReference<Entity>>();
	
	private final Map<String,TeleportReciever> teleportRecievers = new HashMap<String,TeleportReciever>();
	private final Map<String,TriggerSource> triggers = new HashMap<String,TriggerSource>();
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
		//trigger grammar: (" id | prerequisite 1, pre2 ... | result effect 1, res 2 ... ");
		//not whitespace sensitive
		
		//a few trigger examples //
		
		parseTrigger("ed1 | 01_enemy_rawr1 death | notify rawr1 died");
		parseTrigger("ed2 | 01_enemy_rawr2 death | notify rawr2 died");
		//no id, prerequisite, two comma separated effects, note the lack of contents for the textField to indicate empty.
		parseTrigger("01_npc_bob death | 01_npc_bob textField, remove t2");
		//id, prerequisite, effect
		parseTrigger("t2 | 01_cage1 death | 01_npc_bob textField Thanks :)");
		parseTrigger("t2 | place enemy rawr 20 16 ");
		//no id, no prerequisite
		parseTrigger("none | 01_npc_bob textField Save meeeeeeeeee!");
		destroy();
	}
	
	/**
	 * Ensures all useless structures are cleared (since triggers may force some instances).
	 */
	private void destroy() {
		textFields.clear();
		triggers.clear();
		teleportRecievers.clear();
		entityReference.clear();
		parseQueue.clear();
		inst = null;
	}
	
	/**
	 * Parses and adds a new trigger effector based on the string given.<br />
	 * The trigger should follow the general grammar specified:<br />
	 * "id | prerequisite 1, pre2 ... | result effect 1, res 2 ..."<br />
	 * Note that this is not whitespace sensitive. Pipes (|) or commas (,) may not be included elsewhere, even if escaped.
	 */
	private void parseTrigger(String trigger) {
		String[] parts = trigger.split("\\|");
		if(parts.length == 3){
			parseTrigger(parts[0],parts[1],parts[2]);
		}else if(parts.length == 2){
			parseTrigger(null,parts[0],parts[1]);
		}else {
			throw new IllegalArgumentException("Failed to split '" + trigger + "'.");
		}
	}
	
	private void parseTrigger(final String idRaw, final String condRaw, final String effectRaw) {
		//id will eventually be used for triggers referencing other triggers.
		final String id = idRaw == null ? null : idRaw.trim();
		//an array of conditions initialised from a comma separated list.
		final String[] condParts = condRaw.split(",");
		//an array of resulting effects initialised from a comma separated list.
		final String[] effectParts = effectRaw.split(",");
		
		//consume any stray outer whitespace
		for(int i=0;i<condParts.length;i++){
			condParts[i] = condParts[i].trim();
		}
		for(int i=0;i<effectParts.length;i++){
			effectParts[i] = effectParts[i].trim();
		}
		
		final CompositeTrigger trigger = new CompositeTrigger(id,id != null,id != null);
		
		if(id != null){
			triggerReference.put(id, trigger);
		}
		
		for(String ePart : effectParts){
			trigger.addEffect(getTriggerEffect(ePart));
		}
		for(String cPart : condParts){
			parseAttachEntityTriggerCondition(trigger, cPart);
		}
		
		//initially check the trigger for expectation completion (i.e. if no triggers were specified), 
		//else this would likely never be triggered.
		trigger.check();
	}
	
	/**
	 * Parses the trigger condition from the string specified, attaches the trigger appropriately, 
	 *  	and returns the appropriate expected entity from the trigger.
	 * @return An entity associated with the trigger identified. 
	 *  	If the string specified is null, empty ("") or equal to "none" (case insensitive), 
	 *  	no action will be taken and the method will return null.
	 */
	private void parseAttachEntityTriggerCondition(final CompositeTrigger trigger, String cPart) {
		if(cPart == null || cPart.isEmpty() || cPart.equalsIgnoreCase("none")){
			return;
		}
		//greedy split based on whitespace to get word items
		String[] parts = cPart.split("\\s+");
		
		if(parts.length == 1){
			// should be of the form [trigger id]
			final CompositeTrigger t = getTrigger(parts[0]);
			t.addEffect(new VoidAugmentedTriggerEffect<Entity>() {
				@Override
				public void triggered() {
					trigger.triggered(t);
				}
			});
			trigger.addExpected(t);
		}else if(parts.length == 2){
			// should be of the form [src entity id] [action]
			
			WeakReference<Entity> srcER = entityReference.get(parts[0]);
			Entity srcE;
			
			if(srcER == null || (srcE = srcER.get()) == null){
				System.out.println("Warning: Failed to parse '" + cPart + "': No such source id: '" + parts[0] + "'.");
			}else if(parts[1].equalsIgnoreCase("death")){
				if(!(srcE instanceof DestructibleEntity)){
					System.out.println("Warning: Failed to parse '" + cPart + "': Entity: '" + parts[0] + "' may not hold a death trigger.");
					return;
				}
				((DestructibleEntity) srcE).addDeathTrigger(trigger);
				trigger.addExpected(srcE);
			}
		}
		return;
	}
	
	/**
	 * Returns a new trigger effect based on the string specified.<br />s
	 * Accepted values:<br />
	 * notify ([text1] [text2] ...)<br />
	 * [NPC/TextField id] textField ([text1] [text2] ...)
	 * remove [trigger id]
	 * place [enemy/NPC] [sub-type] [x] [y]
	 */
	private VoidAugmentedTriggerEffect<? super Entity> getTriggerEffect(final String effect) {
		try{
			Scanner in = new Scanner(effect);
			String dst = in.next();
			if(dst.equalsIgnoreCase("notify")){
				final String notify = in.nextLine();
				return new VoidAugmentedTriggerEffect<Entity>() {
					@Override
					public void triggered() {
						Notification.addNotification(notify);
					}
				};
			}else if(dst.equalsIgnoreCase("place")){
				boolean enemy = in.next().equalsIgnoreCase("enemy");
				final String subtype = in.next();
				final int x = in.nextInt(), y = in.nextInt();
				if(enemy){
					return new VoidAugmentedTriggerEffect<Entity>() {
						@Override
						public void triggered() {
							Cell c = MapLoader.getCurrentCell();
							Enemy e = Enemy.getNew(subtype, x, y);
							System.out.println("Adding " + e + " to " + c);
							c.addMovingEntity(e);
						}
					};
				}else{
					return new VoidAugmentedTriggerEffect<Entity>() {
						@Override
						public void triggered() {
							MapLoader.getCurrentCell().addMovingEntity(NPC.getNew(subtype, x, y));
						}
					};
				}
			}else if(dst.equalsIgnoreCase("remove")){
				final String id = in.next();
				return new VoidAugmentedTriggerEffect<Entity>() {
					@Override
					public void triggered() {
						removeTrigger(id);
					}
				};
			}else{
				final WeakReference<Entity> dstER = entityReference.get(dst);
				if(dstER == null || dstER.get() == null){
					throw new IllegalArgumentException("No such id: '" + dst + "'.");
				}
				final String res = in.next();
				if(res.equalsIgnoreCase("textField")){
					final String txt = in.hasNextLine() ? in.nextLine() : "";
					if(dstER.get() instanceof NPC){
						return new VoidAugmentedTriggerEffect<Entity>() {
							@Override
							public void triggered() {
								NPC npc = ((NPC) dstER.get());
								if(npc != null){
									TextField<?> tf = npc.getTextField();
									if(tf != null){
										tf.setText(txt);
									}
								}
							}
						};
					}else if(dstER.get() instanceof TextField<?>){
						return new VoidAugmentedTriggerEffect<Entity>() {
							@Override
							public void triggered() {
								TextField<?> tf = ((TextField<?>) dstER.get());
								if(tf != null){
									tf.setText(txt);
								}
							}
						};
					}
					throw new IllegalArgumentException("Entity: '" + dst + "' may not set text field contents.");
				}
			}
			return null;
		}catch(IllegalArgumentException e){
			System.out.println("Warning: Failed to parse '" + effect + "': " + e.getMessage());
			return null;
		}
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
			Enemy e = Enemy.getNew(subtype, x,y);
			cell.addDefaultMovingEntity(e);
			entityReference.put(id, new WeakReference<Entity>(e));
		}else if(go.type.equalsIgnoreCase("npc")){
			NPC npc = NPC.getNew(subtype, x,y);
			String tfstr = go.props.getProperty("text_id");
			if(tfstr != null){
				TextField<?> tf = textFields.get(tfstr);
				if(tf != null){
					npc.setTextField(tf);
				}
			}
			cell.addDefaultMovingEntity(npc);
			entityReference.put(id, new WeakReference<Entity>(npc));
		}else if(go.type.equalsIgnoreCase("door")){
			Door d = new Door(cell,x,y);
			String ts = go.props.getProperty("triggers");
			if(ts != null){
				for(String tid : ts.split("\\s+")) {
					TriggerSource trig = triggers.get(tid);
					trig.addTriggerEffect(d);
					d.addTriggerSource(trig);
				}
			}
			cell.addStaticEntity(d);
			entityReference.put(id, new WeakReference<Entity>(d));
		}else if(go.type.equalsIgnoreCase("doorTrigger")){
			DoorTrigger dt = new DoorTrigger(x,y);
			if(id != null){
				triggers.put(id, dt);
			}
			cell.addStaticEntity(dt);
			entityReference.put(id, new WeakReference<Entity>(dt));
		}else if(go.type.equalsIgnoreCase("doorProjectileTrigger")){
			DoorProjectileTrigger dpt = new DoorProjectileTrigger(x,y);
			if(id != null){
				triggers.put(id, dpt);
			}
			cell.addStaticEntity(dpt);
			entityReference.put(id, new WeakReference<Entity>(dpt));
		}else if(go.type.equalsIgnoreCase("leafTest")){
			LeafTest lt = new LeafTest(x,y);
			cell.addStaticEntity(lt);
			entityReference.put(id, new WeakReference<Entity>(lt));
		}else if(go.type.equalsIgnoreCase("jumpPlatform")){
			JumpPlatform jp = new JumpPlatform(x,y,width);
			cell.addStaticEntity(jp);
			entityReference.put(id, new WeakReference<Entity>(jp));
		}else if(go.type.equalsIgnoreCase("cage")){
			Cage c = new Cage(cell, x,y,width,height);
			cell.addDefaultDestructibleEntity(c);
			entityReference.put(id, new WeakReference<Entity>(c));
		}else if(go.type.equalsIgnoreCase("textField")){
			TextField<?> tf = TextField.newTextField(x,y,width,height,go.props);
			if(id != null){
				textFields.put(id, tf);
			}
			cell.addStaticEntity(tf);
			entityReference.put(id, new WeakReference<Entity>(tf));
		}else if(go.type.equalsIgnoreCase("teleport_recieve")){
			TeleportReciever tr = new TeleportReciever(cell, x, y, width, height);
			teleportRecievers.put(id,tr);
			cell.addStaticEntity(tr);
			entityReference.put(id, new WeakReference<Entity>(tr));
		}else if(go.type.equalsIgnoreCase("teleport_send")){
			String dest = go.props.getProperty("dest");
			TeleportSender ts = new TeleportSender(teleportRecievers.get(dest), x, y, width, height);
			cell.addStaticEntity(ts);
			entityReference.put(id, new WeakReference<Entity>(ts));
		}else if(go.type.equalsIgnoreCase("waterSurfaceEffect")){
			WaterSurfaceEffect wse = new WaterSurfaceEffect(x, y, width);
			cell.addStaticEntity(wse);
			entityReference.put(id, new WeakReference<Entity>(wse));
		}else if(go.type.equalsIgnoreCase("weapon_item_stick")){
			StickItem si = new StickItem(x, y, width, height);
			cell.addDefaultDestructibleEntity(si);
			entityReference.put(id, new WeakReference<Entity>(si));
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
		WEAPON_ITEM_STICK,
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
