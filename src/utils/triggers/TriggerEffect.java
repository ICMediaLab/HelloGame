package utils.triggers;

public interface TriggerEffect {
	void addTriggerSource(TriggerSource t);
	void triggeredSource(TriggerSource t);
}
