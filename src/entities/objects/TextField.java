package entities.objects;

import java.util.Properties;

import map.Cell;
import map.MapLoader;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import utils.HorizontalAlign;
import utils.VerticalAlign;
import entities.MovingEntity;
import entities.StaticEntity;
import entities.players.Player;

public class TextField<S extends Shape> extends StaticEntity<S> {

	private static final boolean TEXTFIELD_BOUNDS_DEBUG = false;
	
	private static final int TEXTFIELD_DEFAULT_LAYER = 10000;
	
	private static final HorizontalAlign DEFAULT_HORIZONTAL_ALIGN = HorizontalAlign.CENTRE;
	private static final VerticalAlign DEFAULT_VERTICAL_ALIGN = VerticalAlign.TOP;
	private static final Color DEFAULT_FIELDCOLOUR = TEXTFIELD_BOUNDS_DEBUG ? Color.red : Color.transparent;
	private static final Color DEFAULT_TEXTCOLOUR = Color.white;
	private static final int DEFAULT_FADEIN = 50;
	private static final int DEFAULT_FADEOUT = 50;
	private static final int DEFAULT_TEXT_PADDING = 10;
	
	private final VerticalAlign vAlign;
	private final HorizontalAlign hAlign;
	private final float fadeIn, fadeOut;
	private float filter = 0f;
	private String str;
	private Color fieldColour, textColour;
	
	public TextField(String str, S shape, VerticalAlign valign){
		this(str,shape,valign,DEFAULT_HORIZONTAL_ALIGN);
	}
	
	public TextField(String str, S shape, VerticalAlign valign, HorizontalAlign halign) {
		this(str,shape,valign,halign,DEFAULT_FIELDCOLOUR,DEFAULT_TEXTCOLOUR,DEFAULT_FADEIN,DEFAULT_FADEOUT);
	}
	
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
	public TextField(String str, S shape, VerticalAlign valign, HorizontalAlign halign, Color fieldColour, Color textColour, int fadeIn, int fadeOut) {
		super(shape);
		this.vAlign = valign;
		this.hAlign = halign;
		this.str = str;
		this.fieldColour = fieldColour;
		this.textColour = textColour;
		this.fadeIn = 1.0f/fadeIn;
		this.fadeOut = 1.0f/fadeOut;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		Shape s = getHitbox();
		
		g.setColor(fieldColour.scaleCopy(filter));
		g.draw(s.transform(Cell.SHAPE_DRAW_TRANSFORM));
		
		g.setColor(textColour.scaleCopy(filter));
		float y = vAlign.getY(s.getY(), s.getHeight(),DEFAULT_TEXT_PADDING,g.getFont(),str);
		float x = hAlign.getX(s.getX(), s.getWidth() ,DEFAULT_TEXT_PADDING,g.getFont(),str);
		g.drawString(str, x,y);
	}

	@Override
	public void update(GameContainer gc) {
		Player player = MapLoader.getCurrentCell().getPlayer();
		if (intersects(player) || contains(player)) {
			if (filter < 1f) {
				filter = Math.min(1, filter + fadeIn);
			}
		} else {
			if (filter > 0f) {
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

	@Override
	public boolean isSolid() {
		return false;
	}

	public static TextField<?> newTextField(int x, int y, int width,
			int height, Properties prop) {
		String content = prop.getProperty("text");
		VerticalAlign valign = VerticalAlign.parseAlignment(prop.getProperty("valign"),DEFAULT_VERTICAL_ALIGN);
		HorizontalAlign halign = HorizontalAlign.parseAlignment(prop.getProperty("halign"),DEFAULT_HORIZONTAL_ALIGN);
		return new TextField<Rectangle>(content, new Rectangle(x, y, width, height), valign, halign);
	}

	public void setText(String str) {
		this.str = str;
	}
}
