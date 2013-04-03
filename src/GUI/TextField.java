package GUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import utils.MapLoader;
import entities.MovingEntity;
import entities.StaticEntity;

public class TextField<S extends Shape> extends StaticEntity<S> {
	
	private static final int TEXTFIELD_DEFAULT_LAYER = 10000;
	
	private final float textOffsetX, textOffsetY;
	private final float fadeIn, fadeOut;
	private float filter = 0;
	private String str = "Text";
	private Color fieldColour, textColour;

	/**
	 * 
	 * @param str Text to be displayed
	 * @param shape The shape of the trigger
	 * @param textOffsetX Distance of centre of text from trigger circle
	 * @param textOffsetY Distance of centre of text from trigger circle
	 * @param fieldColour Colour of text field
	 * @param textColour Colour of text
	 * @param fadeIn Fade in time
	 * @param fadeOut Fade out time
	 */
	public TextField(String str, S shape, float textOffsetX, float textOffsetY, Color fieldColour, Color textColour, int fadeIn, int fadeOut) {
		super(shape);
		this.str = str;
		this.textOffsetX = textOffsetX;
		this.textOffsetY = textOffsetY;
		this.fieldColour = fieldColour;
		this.textColour = textColour;
		this.fadeIn = 1.0f/fadeIn;
		this.fadeOut = 1.0f/fadeOut;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		Shape s = getHitbox();
		
		g.setColor(fieldColour.scaleCopy(filter));
		g.draw(s);
		
		g.setColor(textColour.scaleCopy(filter));
		g.drawString(str, s.getCenterX() - g.getFont().getWidth(str)/2 + textOffsetX,
				s.getCenterY() - g.getFont().getHeight(str)/2 + textOffsetY);
	}

	@Override
	public void update(GameContainer gc) {
		if (contains(MapLoader.getCurrentCell().getPlayer())) {
			if (filter < 1) {
				filter = Math.min(1, filter + fadeIn);
			}
		} else {
			if (filter < 1) {
				filter = Math.max(0, filter - fadeOut);
			}
		}
	}

	@Override
	public void stop_sounds() {
		// nothing to do here. :/
	}

	@Override
	public void collide(MovingEntity e) {
		// nothing to do here :)
	}

	@Override
	public int getLayer() {
		return TEXTFIELD_DEFAULT_LAYER;
	}
}
