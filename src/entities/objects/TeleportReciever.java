package entities.objects;

import map.Cell;
import map.MapLoader;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import utils.interval.one.Interval;
import entities.MovingEntity;
import entities.StaticRectEntity;
import entities.players.Player;

public class TeleportReciever extends StaticRectEntity {
	
	private static final long serialVersionUID = -9119450080661765828L;
	
	private final Cell cell;
	
	public TeleportReciever(Cell c, float x, float y, float width, float height) {
		super(x, y, width, height);
		cell = c;
	}
	
	public void recieve() {
		MapLoader.setCurrentCell(cell);
		Player p = cell.getPlayer();
		p.setPosition(new Interval(getX(),getX()+getWidth()-p.getWidth()).random(),new Interval(getY(),getY()+getHeight()-p.getHeight()).random());
	}

	@Override
	public void collide(MovingEntity e) {
		//ignore
	}

	@Override
	public void update(GameContainer gc) {
		//ignore
	}

	@Override
	public int getLayer() {
		return 0; //who cares...
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		//ignore...
	}

	@Override
	public boolean isSolid() {
		return false;
	}

}
