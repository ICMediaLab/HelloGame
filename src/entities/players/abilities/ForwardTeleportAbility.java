package entities.players.abilities;

import map.tileproperties.TileProperty;
import utils.MapLoader;
import entities.players.Player;
import game.config.Config;

public class ForwardTeleportAbility extends PlayerAbility {
	
	private static float distance = 5f;
	
	public ForwardTeleportAbility() {
		this.name = "Forward teleport";
		this.description = "Press Q to teleport forward";
	}
	
    public void use(Player p){
		if (p.getDirection() == 1 && (p.getX() - 1 + distance < Config.getScreenWidth()/Config.getTileSize())){
			if (MapLoader.getCurrentCell().getTile((int) p.getX() + (int) distance, (int) p.getY()).lookupProperty(TileProperty.BLOCKED).getBoolean()){
				p.accelerate(distance,0);
			}
		} else if (p.getDirection() == -1 && (p.getX() - 1 - distance > 0)) {
			if (MapLoader.getCurrentCell().getTile((int) p.getX() - (int) distance, (int) p.getY()).lookupProperty(TileProperty.BLOCKED).getBoolean()){
				p.accelerate(-distance,0);
			}
		}
	}

	public static float getDistance() {
		return distance;
	}

	public static void setDistance(float distance) {
		ForwardTeleportAbility.distance = distance;
	}
}
