package GUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.RoundedRectangle;

public class TextField extends RoundedRectangle {
	
	private static final long serialVersionUID = 7699309243884769845L;

	public TextField(float x, float y, float width, float height,
			float cornerRadius) {
		super(x, y, width, height, cornerRadius);
	}

	public void render(Graphics g, Color buttonColour, Color textColour) {
		Color oldColour = g.getColor();
		
		g.setColor(buttonColour);
		g.fill(this);
		
		g.setColor(textColour);

		g.setColor(oldColour);
	}
	
}
