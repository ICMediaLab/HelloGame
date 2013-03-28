package utils;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import entities.Entity;

public class ContactListenerSlick implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		// Collide Entity A with Entity B
		((Entity) contact.getFixtureA().getBody().getUserData()).collide((Entity) contact.getFixtureB().getBody().getUserData());
	}

	@Override
	public void endContact(Contact contact) {
		((Entity) contact.getFixtureA().getBody().getUserData()).endCollide((Entity) contact.getFixtureB().getBody().getUserData());
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		
	}

}
