package utils.triggers;

public interface Triggerable {
	
	void addTrigger(Trigger t);
	void removeTrigger(Trigger t);
	void clearTriggers();
	
	void triggered(Trigger t);
	void untriggered(Trigger t);

}
