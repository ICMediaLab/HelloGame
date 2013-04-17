package utils.classfinder;


public abstract class AbstractClassMapFunctor<V> extends AbstractClassSetFunctor<V> implements ClassMapFunctor<String, V>{
	
	@Override
	public V getValue(Class<?> clazz) {
		return getEntry(clazz).getValue();
	}
	
}
