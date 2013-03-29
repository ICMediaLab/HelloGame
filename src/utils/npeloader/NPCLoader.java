package utils.npeloader;

import java.util.Collection;

import org.w3c.dom.Document;

import entities.npcs.NPC;

/**
 * A utility class for loading npc templates from XML documents from specified {@code String} paths.<br />
 * Note additionally that the npc template storage will not be automatically emptied unless {@code clearLoaded()} is run. 
 */
public final class NPCLoader extends Loader<NPC> {
	
	private static final String root="npcs",node="npc",lookup="name";
	
	public NPCLoader(){
		super(NPC.class);
	}
	
	/**
	 * Loads specific npcs into the npc template storage from a given XML document object.
	 */
	@Override
	protected final void load(Document d, Collection<String> enemiesToLoad){
		try {
			loadOnly(d, root, node, lookup, enemiesToLoad);
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
}
