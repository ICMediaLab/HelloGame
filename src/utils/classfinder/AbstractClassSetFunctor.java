package utils.classfinder;

import java.lang.reflect.Modifier;

public abstract class AbstractClassSetFunctor<V> implements ClassSetFunctor<V> {
	
	@Override
	public boolean include(Class<?> clazz) {
		int mod = clazz.getModifiers();
		return !Modifier.isAbstract(mod) && 
				!Modifier.isInterface(mod) && 
				 Modifier.isPublic(mod); 
	}
	
	public Object getObject(Class<?> clazz) throws InstantiationException, IllegalAccessException {
		return clazz.newInstance();
	}
}
