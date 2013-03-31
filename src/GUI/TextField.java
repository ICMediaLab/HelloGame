package GUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import entities.players.Player;
import game.config.Config;

public class TextField {
	
	private float x, y, textOffsetX, textOffsetY, filter = 0;
	private float fadeIn, fadeOut = 0;
	private Circle circle;
	private Player player;
	private String str = "Text";
	private Color fieldColour, textColour;
	private int cornerRadius, counterOut = 0;
	private int counterIn = 0;

	

	public TextField(String str, float triggerX, float triggerY, int cornerRadius, float triggerRadius, Player player, float textOffsetX, float textOffsetY, Color fieldColour, Color textColour, float fadeIn, float fadeOut) {
		this.player = player;
		this.str = str;
		this.textOffsetX = textOffsetX;
		this.textOffsetY = textOffsetY;
		this.x = triggerX;
		this.y = triggerY;
		this.cornerRadius = cornerRadius;
		this.fieldColour = fieldColour;
		this.textColour = textColour;
		this.fadeIn = fadeIn;
		this.fadeOut = fadeOut;
		this.counterOut = (int) fadeOut;
		circle = new Circle(x + triggerRadius, y + triggerRadius, triggerRadius);
	}

	public void render(Graphics g) {
		Color oldColour = g.getColor();
		
		if (circle.contains((player.getCentreX() - 1)*Config.getTileSize(), (player.getCentreY() - 1)*Config.getTileSize())) {
			if (counterIn < fadeIn && filter<=(1 - 1/fadeIn)) {
				counterIn++;
				filter += 1/fadeIn;
			}
			counterOut = 0;
		} else {
			if (counterOut < fadeOut && filter>=1/fadeOut) {
				counterOut++;
				filter -= 1/fadeOut;
			}
			counterIn = 0;
		}
		g.setColor(fieldColour.scaleCopy(filter));
		g.fillRoundRect(x - g.getFont().getWidth(str)/2 + textOffsetX - 5, y - g.getFont().getHeight(str)/2 + textOffsetY - 5, g.getFont().getWidth(str) + 10, g.getFont().getHeight(str) + 10, cornerRadius);
		
		g.setColor(textColour.scaleCopy(filter));
		g.drawString(str, x - g.getFont().getWidth(str)/2 + textOffsetX, y - g.getFont().getHeight(str)/2 + textOffsetY);
		
		g.setColor(oldColour);
	}
	
	
	
}
