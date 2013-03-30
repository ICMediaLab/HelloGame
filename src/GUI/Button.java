package GUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.RoundedRectangle;

public class Button extends RoundedRectangle {

	private static final long serialVersionUID = 7699309243884769844L;
	
	private final String str;
	
	public Button(String text, float x, float y, float width, float height,
			float cornerRadius) {
		super(x, y, width, height, cornerRadius);
		str = text;
	}
	
	public void render(Graphics g, Color buttonColour, Color textColour) {
		Color oldColour = g.getColor();
		
		g.setColor(buttonColour);
		g.fill(this);
		
		g.setColor(textColour);
		float width = g.getFont().getWidth(str);
		float height = g.getFont().getHeight(str);
		int x = (int) (getX() + (getWidth() - width)/2f);
		int y = (int) (getY() + (getHeight() - height)/2f);
		g.drawString(str, x, y);
		
		g.setColor(oldColour);
	}

}
