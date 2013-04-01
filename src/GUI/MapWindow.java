package GUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

class MapWindow extends AbstractWindow {
	
	public MapWindow(GUI gui) {
		super(gui);
	}

	
	// TODO: It would be nice to display actual maps inside those boxes so player could recognise the cells. It would need to be 1 tile = 1 pixel I think
	@Override
	public void render(GameContainer gc, Graphics g) {
		float x = getX(), y = getY(), width = getWidth(), height = getHeight();
		g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.8f));
		g.fillRoundRect(x, y, width, height, 5);
		
		g.setColor(Color.black);
		g.drawString("Map needs to be drawn below", x + (width - g.getFont().getWidth("Map needs to be drawn below")) * 0.5f , y + 20);
		g.drawString("Orange - current cell", x + 20, y + 50);
		g.drawString("Green - explored cell", x + 20, y + 70);
		g.drawString("White - unexplored cell", x + 20, y + 90);
		g.drawString("Line - connection", x + 20, y + 110);
		g.drawString("Dot - contains item", x + 20, y + 130);
		
		g.setColor(Color.orange);
		g.fillRoundRect(x + width/2 - 3, y + height/2 - 3, 46, 46, 5);
		
		g.setColor(Color.green);
		g.fillRoundRect(x + width/2 - 3 + 50, y + height/2 - 3, 46, 46, 5);
		g.fillRoundRect(x + width/2 - 3 + 100, y + height/2 - 3, 46, 46, 5);
		
		g.setColor(Color.white);
		g.fillRoundRect(x + width/2 - 3 + 100, y + height/2 - 3 + 50, 46, 46, 5);
		
		g.setColor(Color.darkGray);
		g.fillRoundRect(x + width/2, y + height/2, 40, 40, 5);
		g.fillRoundRect(x + width/2 + 50, y + height/2, 40, 40, 5);
		g.fillRoundRect(x + width/2 + 100, y + height/2, 40, 40, 5);
		g.fillRoundRect(x + width/2 + 100, y + height/2 + 50, 40, 40, 5);
		
		g.drawLine(x + width/2 + 40, y + height/2 + 20, x + width/2 + 50, y + height/2 + 20);
		g.drawLine(x + width/2 + 40 + 50, y + height/2 + 20, x + width/2 + 50 + 50, y + height/2 + 20);
		g.drawLine(x + width/2 + 120, y + height/2 + 40, x + width/2 +	120, y + height/2 + 50);
		

        g.setColor(Color.cyan);
        g.fillOval(x + width/2 - 3 + 120, y + height/2 + 17, 6, 6);
		
	}

	@Override
	public void update(GameContainer gc) {
		
	}

}
