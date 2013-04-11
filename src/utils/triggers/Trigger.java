package utils.triggers;

public interface Trigger {
	void addTriggerable(Triggerable t);
	void removeTriggerable(Triggerable t);
	void clearTriggerables();
	
	void triggered();
	void untriggered();
}
