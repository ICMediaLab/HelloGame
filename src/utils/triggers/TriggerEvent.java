package utils.triggers;

import items.Weapon;

import java.util.HashSet;
import java.util.Set;

import map.Cell;
import notify.Notification;
import entities.Entity;
import entities.enemies.Enemy;
import entities.npcs.NPC;
import entities.objects.Door;

public class TriggerEvent<K> implements AugmentedTriggerSource<K> {
	private TriggerEvent(){ }
	
	public static final TriggerEvent<Weapon> PLAYER_ITEM_PICKUP = new TriggerEvent<Weapon>();
	public static final TriggerEvent<Cell> CELL_TRANSITION = new TriggerEvent<Cell>();
	public static final TriggerEvent<Entity> ENTITY_REMOVED = new TriggerEvent<Entity>();
	public static final TriggerEvent<Door> DOOR_OPENED = new TriggerEvent<Door>();
	
	private final Set<AugmentedTriggerEffect<K>> effects = new HashSet<AugmentedTriggerEffect<K>>();
	
	public void triggered(K k){
		for(AugmentedTriggerEffect<K> effect : effects){
			effect.triggered(k);
		}
	}
	
	@Override
	public void addTriggerEffect(AugmentedTriggerEffect<K> t) {
		effects.add(t);
	}
	
	private abstract static class StaticEventTrigger<K> implements AugmentedTriggerEffect<K> {
		public StaticEventTrigger(TriggerEvent<K> event) {
			event.addTriggerEffect(this);
		}
	}
	
	@SuppressWarnings("unused")
	private static final StaticEventTrigger<Weapon> STICK_NOTIFY_TRIGGER = new StaticEventTrigger<Weapon>(PLAYER_ITEM_PICKUP) {
		public void triggered(Weapon k) {
			Notification.addNotification("You picked up the " + k.toString().toLowerCase() + "! Press W or click the left mouse button to use it.");
		}
	};
	
	@SuppressWarnings("unused")
	private static final StaticEventTrigger<Cell> CELL_TRANS_NOTIFY_TRIGGER = new StaticEventTrigger<Cell>(CELL_TRANSITION) {
		public void triggered(Cell k) {
			Notification.addNotification("Transitioned to cell " + k + ".");
		}
	};
	
	@SuppressWarnings("unused")
	private static final StaticEventTrigger<Entity> ENTITY_DEATH_NOTIFY_TRIGGER = new StaticEventTrigger<Entity>(ENTITY_REMOVED) {
		public void triggered(Entity k) {
			if(k instanceof NPC || k instanceof Enemy){
				Notification.addNotification(k + " died!");
			}
		}
	};
	
	@SuppressWarnings("unused")
	private static final StaticEventTrigger<Door> DOOR_OPEN_NOTIFY_TRIGGER = new StaticEventTrigger<Door>(DOOR_OPENED) {
		public void triggered(Door k) {
			Notification.addNotification("A door opened!");
		}
	};
}
