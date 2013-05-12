package utils.classfinder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Utility class for loading all class instances of a particular package.
 * @author gds12
 */
public final class ClassFinder {
	private static final int classlen = ".class".length();
	
	private static List<Class<?>> getClasses(String packageName) 
			throws IOException {
		ClassLoader cLoader = Thread.currentThread().getContextClassLoader();
		List<Class<?>> classes = new LinkedList<Class<?>>();
		if(cLoader != null){
			String path = packageName.replace('.', '/');
			Enumeration<URL> resources = cLoader.getResources(path);
			List<File> dirs = new LinkedList<File>();
			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				dirs.add(new File(URLDecoder.decode(resource.getFile(),"UTF-8")));
			}
			for (File directory : dirs) {
				findClasses(directory, packageName, classes);
			}
		}
		return classes;
	}
	
	private static List<Class<?>> findClasses(File dir, String packageName, List<Class<?>> classes) {
		if (dir.exists()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					findClasses(file,packageName + "." + file.getName(),classes);
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
}
