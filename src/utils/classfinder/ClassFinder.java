package utils.classfinder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import entities.players.abilities.PlayerAbility;

/**
 * Utility class for loading all class instances of a particular package.
 * @author gds12
 */
public final class ClassFinder {
	private static final int classlen = ".class".length();
	
	private static List<Class<?>> getClasses(String packageName) 
			throws IOException {
		ClassLoader cLoader = Thread.currentThread().getContextClassLoader();
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		if(cLoader != null){
			String path = packageName.replace('.', '/');
			Enumeration<URL> resources = cLoader.getResources(path);
			List<File> dirs = new LinkedList<File>();
			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				dirs.add(new File(URLDecoder.decode(resource.getFile(),"UTF-8")));
			}
			for (File directory : dirs) {
				classes.addAll(findClasses(directory, packageName));
			}
		}
		return classes;
	}
	
	private static List<Class<?>> findClasses(File dir, String packageName) {
		List<Class<?>> classes = new LinkedList<Class<?>>();
		if (dir.exists()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				System.out.println("Found " + file.getName());
				if (file.isDirectory()) {
					classes.addAll(findClasses(file, 
							packageName + "." + file.getName()));
				} else if(file.isFile() && file.getName().endsWith(".class")){
					try {
						classes.add(Class.forName(packageName + '.' + 
								file.getName().substring(0, file.getName().length()
										- classlen)));
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return classes;
	}
	
	/**
	 * Returns a mapping given a package of classes and a functor to interact with.
	 * @param packge A local package to search. Will not work with urls or java system files.
	 * @param cmf The functor to determine the pairings of the map. See {@link ClassMapFunctor}.
	 * @throws IOException
	 */
	public static <K,V> Map<K, V> getClassMap(String packge, ClassMapFunctor<K,V> cmf) throws IOException{
		Collection<Class<?>> classes = getClasses(packge);
		Map<K,V> ret = new HashMap<K,V>();
		for(Class<?> c : classes){
			if(cmf.include(c)){
				Entry<K, V> e = cmf.getEntry(c);
				ret.put(e.getKey(), e.getValue());
			}
		}
		return ret;
	}
	
	/**
	 * Returns a set given a package of classes and a functor to interact with.
	 * @param packge A local package to search. Will not work with urls or java system files.
	 * @param cmf The functor to determine the pairings of the map. See {@link ClassMapFunctor}.
	 * @throws IOException
	 */
	public static <V> Set<V> getClassSet(String packge, ClassSetFunctor<V> cmf) throws IOException{
		Collection<Class<?>> classes = getClasses(packge);
		Set<V> ret = new HashSet<V>();
		for(Class<?> c : classes){
			if(cmf.include(c)){
				ret.add(cmf.getValue(c));
			}
		}
		return ret;
	}
	
	//concrete useful implementations
	
	/**
	 * Returns a mapping of {@link String} to {@link PlayerAbility} in the following format:<br />
	 * Key: "Ability.class" suffix removed, assumed present. e.g. DoubleJumpAbility -> doublejump<br />
	 * Value: Concrete, public class instances only.
	 */
	public static Map<String, PlayerAbility> getAbilityMap(){
		try {
			return getClassMap("entities.players.abilities", new AbiltiiesClassMapFunctor());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static class AbiltiiesClassMapFunctor extends AbstractClassMapFunctor<PlayerAbility> {
		
		private static final int abiltylen = "Ability".length();
		
		private String getKey(Class<?> clazz) {
			String sn = clazz.getSimpleName();
			return sn.substring(0,sn.length() - abiltylen).toLowerCase();
		}
		
		@Override
		public PlayerAbility getValue(Class<?> clazz) {
			try {
				Object inst = getObject(clazz);
				if(inst instanceof PlayerAbility){
					return (PlayerAbility) inst;
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public Entry<String, PlayerAbility> getEntry(Class<?> clazz) {
			return new SimpleEntry<String,PlayerAbility>(getKey(clazz),getValue(clazz));
		}
	}
	
	/**
	 * Returns a mapping of {@link String} to {@link PlayerAbility} in the following format:<br />
	 * Key: "Ability.class" suffix removed, assumed present. e.g. DoubleJumpAbility -> doublejump<br />
	 * Value: Concrete, public class instances only.
	 */
	public static Set<PlayerAbility> getAbilitySet(){
		try {
			return getClassSet("entities.players.abilities", new AbiltiiesClassMapFunctor());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
