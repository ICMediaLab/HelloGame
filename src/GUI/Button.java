package GUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.RoundedRectangle;

import utils.Position;
import utils.mouse.MouseContainer;

public class Button extends RoundedRectangle {

	private static final long serialVersionUID = 7699309243884769844L;
	
	private static final Color DEFAULT_BUTTON = Color.darkGray, DEFAULT_TEXT = Color.white;
	
	private final String str;
	
	public Button(String text, float x, float y, float width, float height,
			float cornerRadius) {
		super(x, y, width, height, cornerRadius);
		str = text;
	}
	
	public void render(Graphics g){
		render(g, DEFAULT_BUTTON, DEFAULT_TEXT);
	}
	
	public void render(Graphics g, Color buttonColour, Color textColour) {
		Color oldColour = g.getColor();
		
		g.setColor(buttonColour);
		g.fill(this);
		
		g.setColor(textColour);
		float textWidth = g.getFont().getWidth(str);
		float textHeight = g.getFont().getHeight(str);
		int x = (int) (getX() + (getWidth() - textWidth)/2f);
		int y = (int) (getY() + (getHeight() - textHeight)/2f);
		g.drawString(str, x, y);

		g.setColor(oldColour);
	}
	
	public boolean contains(MouseContainer mc){
		return contains(mc.getX(), mc.getY());
	}
	
	public boolean contains(Position p){
		return contains(p.getX(), p.getY());
	}

}
