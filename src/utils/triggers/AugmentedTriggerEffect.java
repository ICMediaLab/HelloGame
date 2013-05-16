package utils.triggers;

public interface AugmentedTriggerEffect<K> {
	void triggered(AugmentedTriggerSource<K> t, K k);
}
