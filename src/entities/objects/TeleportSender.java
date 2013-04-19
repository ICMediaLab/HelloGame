package entities.objects;

import map.MapLoader;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


import entities.MovingEntity;
import entities.StaticRectEntity;

public class TeleportSender extends StaticRectEntity {

	private final TeleportReciever tr;

	public TeleportSender(TeleportReciever tr, float x, float y, float width, float height) {
		super(x, y, width, height);
		this.tr = tr;
	}
	
	@Override
	public void collide(MovingEntity e) {
		if(e == MapLoader.getCurrentCell().getPlayer() && tr != null){
			tr.recieve();
		}
	}
	
	@Override
	public void update(GameContainer gc) {
		//nothing to do here....
	}
	
	@Override
	public int getLayer() {
		return 0; //doesn't matter since invisible :/
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		//do nothing since there's no image...
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}
}
