package GUI;

import org.newdawn.slick.Input;

public enum Window {
	ABILITIES(AbilitiesWindow.class,Input.KEY_I),
	JOURNAL(JournalWindow.class,Input.KEY_J),
	MAP(MapWindow.class,Input.KEY_M),
	MENU(MenuWindow.class,Input.KEY_O);
	
	private final AbstractWindow inst;
	private final int windowKey;
	
	private Window(Class<? extends AbstractWindow> clazz, int windowKey){
		AbstractWindow tryinst = null;
		try {
			tryinst = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		this.inst = tryinst;
		this.windowKey = windowKey;
	}
	
	AbstractWindow getInstance(){
		return inst;
	}
	
	boolean isWindowKeyPressed(Input input){
		return input.isKeyPressed(windowKey);
	}
	
}
