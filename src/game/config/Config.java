package game.config;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * A utility class for getting global variables such as tile size and screen dimensions.<br />
 * Variable assignments can be loaded through the {@code loadConfig} method.
 */
public final class Config {
	private Config(){}; //this class should not be instantiated. 
	
	private static final String CONFIG_FILE_NAME = ".config";
	
	static {
		setDefaultConfig();
		loadConfig();
	}
	
	/**
	 * Sets the default value for configuration variables.
	 */
	private static void setDefaultConfig(){
		MODE_FULLSCREEN = false;
		MODE_VSYNC = true;
		NORMAL_FPS = 60;
		SCREEN_HEIGHT = 600;
		SCREEN_WIDTH = 800;
		TILE_SIZE = 32;
	}
	
	/**
	 * Loads the configuration file from any location in the java class path (will prioritise as a depth-first search).<br />
	 * A call to {@code setDefaultConfig} should be done prior to calling {@code loadConfig} as variables may not be assigned or the file may not be found. 
	 */
	public static void loadConfig() {
		try{
			File configFile = getFile(CONFIG_FILE_NAME);
			if(configFile == null || !configFile.exists()){
				PrintStream def = new PrintStream(ClassLoader.getSystemResource("").getFile() + CONFIG_FILE_NAME);
				System.out.println("creating new file at " + ClassLoader.getSystemResource("").getFile() + CONFIG_FILE_NAME);
				def.println("# This is the default configuration file for HelloGame.");
				def.println("# Any lines prefixed with a # symbol will be ignored.");
				def.println("# Ensure any variables you wish to change from the default values are\n# uncommented.");
				def.println();
				def.println("# If set to true then the game will run in fullscreen mode (defaults to "+MODE_FULLSCREEN+").");
				def.println("# fullscreen = false");
				def.println();
				def.println("# If set to true then VSync will be enabled, which should be used to eliminate\n# image tearing (defaults to " + MODE_VSYNC + ").");
				def.println("# vsync = true");
				def.println();
				def.println("# Determines the default screen resolution (defaults to "+SCREEN_WIDTH+"x"+SCREEN_HEIGHT+").");
				def.println("# screenwidth  = 800");
				def.println("# screenheight = 600");
				def.println();
				def.println("# Determines the maximum frames-per-second (fps) that the game should be\n# allowed to run at (defaults to "+NORMAL_FPS+").");
				def.println("# fps = 60");
				System.out.println("Could not find configuration file in the local directory structure.\nCreating default config file.");
			}else{
				Scanner config = new Scanner(configFile);
				String line;
				while(config.hasNextLine()){
					
					line = config.nextLine().trim();
					if(line.startsWith("#")){
						continue;
					}
					if(line.startsWith("vsync")){
						MODE_VSYNC = Boolean.parseBoolean(trimPastEquals(line));
					}else if(line.startsWith("fullscreen")){
						MODE_FULLSCREEN = Boolean.parseBoolean(trimPastEquals(line));
					}else if(line.startsWith("screenwidth")){
						SCREEN_WIDTH = Integer.parseInt(trimPastEquals(line));
					}else if(line.startsWith("screenheight")){
						SCREEN_HEIGHT = Integer.parseInt(trimPastEquals(line));
					}else if(line.startsWith("fps")){
						NORMAL_FPS = Integer.parseInt(trimPastEquals(line));
					}else if(line.startsWith("tilesize")){
						TILE_SIZE = Integer.parseInt(trimPastEquals(line));
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Utility method to return the latter part of an assignment.
	 * @param line A line of text containing an '=' character.
	 * @return The second half of the assignment, or the original string if no matching character is found.
	 */
	private static String trimPastEquals(String line){
		int pos = line.lastIndexOf('=');
		return line.substring(pos+1).trim();
	}
	
	/**
	 * Recursively (depth-first) searches through directories to find a target file, starting from the java class path.<br />
	 * Will return the first instance of a matching file without searching for conflicts.
	 * @param target The target filename.
	 * @return A file of target filename, or null if no such file is found.
	 * @throws IOException If IO errors occur.
	 */
	private static File getFile(String target) throws IOException{
		Config.class.getClass().getClassLoader();
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Enumeration<URL> urls = cl.getResources("");
		List<File> dirs = new LinkedList<File>();
		while(urls.hasMoreElements()){
			dirs.add(new File(urls.nextElement().getFile()));
		}
		for(File dir : dirs){
			File res = configSearchDir(dir,target);
			if(res != null){
				return res;
			}
		}
		return null;
	}
	
	/**
	 * Recursively (depth-first) searches through directories to find a target file.<br />
	 * Will return the first instance of a matching file without searching for conflicts.
	 * @param dir The current search directory
	 * @param target The target filename
	 * @return A file of target filename, or null if no such file is found.
	 */
	private static File configSearchDir(File dir, String target){
		if(dir.exists()){
			File[] files = dir.listFiles();
			for(File file : files){
				if(file.isDirectory()){
					configSearchDir(file,target);
				}else if(file.isFile() && file.getName().equalsIgnoreCase(target)){
					return file;
				}
			}
		}
		return null;
	}

	/**
	 * Returns true if VSync should be enabled.
	 */
	public static boolean isVsync() {
		return MODE_VSYNC;
	}

	/**
	 * Returns the default screen width.
	 */
	public static int getScreenWidth() {
		return SCREEN_WIDTH;
	}
	
	/**
	 * Returns the default screen height.
	 */
	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	/**
	 * Returns true if the program should be running in fullscreen mode.
	 * @return
	 */
	public static boolean isFullscreen() {
		return MODE_FULLSCREEN;
	}

	/**
	 * Returns the target frames-per-second.
	 * @return
	 */
	public static int getNormalFPS() {
		return NORMAL_FPS;
	}

	/**
	 * Returns the standard tile size.
	 */
	public static int getTileSize() {
		return TILE_SIZE;
	}

	//a bunch of configuration stuff that can probably be moved to an external file... IT WAS :D
	private static boolean MODE_VSYNC;
	private static boolean MODE_FULLSCREEN;
	
	private static int SCREEN_WIDTH;
	private static int SCREEN_HEIGHT;
	private static int NORMAL_FPS;
	private static int TILE_SIZE;
	
}
