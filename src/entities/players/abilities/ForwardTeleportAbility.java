package entities.players.abilities;

import map.tileproperties.TileProperty;
import utils.MapLoader;
import entities.players.Player;

public class ForwardTeleportAbility extends PlayerAbility {
	
	private static float distance = 5f;
	
    public void use(Player p){
		if (p.getDirection() == 1){
			if (MapLoader.getCurrentCell().getTile((int) p.getX() + (int) distance, (int) p.getY()).lookupProperty(TileProperty.BLOCKED).getBoolean()){
				p.accelerate(distance,0);
			}
		} else {
			if (MapLoader.getCurrentCell().getTile((int) p.getX() - (int) distance, (int) p.getY()).lookupProperty(TileProperty.BLOCKED).getBoolean()){
				p.accelerate(-distance,0);
			}
		}
	}

	@Override
	public void stop_sounds() {
		// TODO Auto-generated method stub
		
	}

	public static float getDistance() {
		return distance;
	}

	public static void setDistance(float distance) {
		ForwardTeleportAbility.distance = distance;
	}
}
