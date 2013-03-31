package GUI;

import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Input;

public enum Window {
	ABILITIES(AbilitiesWindow.class,Input.KEY_I),
	JOURNAL(JournalWindow.class,Input.KEY_J),
	MAP(MapWindow.class,Input.KEY_M),
	MENU(MenuWindow.class,Input.KEY_O,Input.KEY_ESCAPE),
	OPTIONS(OptionsWindow.class);
	
	private final Class<? extends AbstractWindow> clazz;
	private final int[] windowKeys;
	private AbstractWindow inst = null;
	
	private Window(Class<? extends AbstractWindow> clazz, int... windowKeys){
		this.clazz = clazz;
		this.windowKeys = windowKeys;
	}
	
	AbstractWindow getInstance(GUI gui){
		if(inst == null){
			try {
				inst = clazz.getConstructor(GUI.class).newInstance(gui);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return inst;
	}
	
	boolean isWindowKeyPressed(Input input){
		for(int key : windowKeys){
			if(input.isKeyPressed(key)){
				return true;
			}
		}
		return false;
	}
	
}
