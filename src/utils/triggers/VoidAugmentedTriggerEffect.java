package utils.triggers;

public abstract class VoidAugmentedTriggerEffect<K> implements AugmentedTriggerEffect<K>{

	@Override
	public void triggered(K k) {
		triggered();
	}

	public abstract void triggered();

}
