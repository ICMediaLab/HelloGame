package utils.triggers;

public interface AugmentedTriggerSource<K> {
	void addTriggerEffect(AugmentedTriggerEffect<K> t);
}
