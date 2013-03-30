package GUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

class MapWindow extends AbstractWindow {

	@Override
	public void render(Graphics gr) {
		float x = getX(), y = getY(), width = getWidth(), height = getHeight();
		gr.setColor(new Color(0.5f, 0.5f, 0.5f, 0.8f));
		gr.fillRoundRect(x, y, width, height, 5);
		
		gr.setColor(Color.black);
		gr.drawString("Map needs to be drawn below", x + (width - gr.getFont().getWidth("Map needs to be drawn below")) * 0.5f , y + 20);
		gr.drawString("Orange - current cell", x + 20, y + 50);
		gr.drawString("Green - explored cell", x + 20, y + 70);
		gr.drawString("White - unexplored cell", x + 20, y + 90);
		gr.drawString("Line - connection", x + 20, y + 110);
		
		gr.setColor(Color.orange);
		gr.fillRoundRect(x + width/2 - 3, y + height/2 - 3, 46, 46, 5);
		
		gr.setColor(Color.green);
		gr.fillRoundRect(x + width/2 - 3 + 50, y + height/2 - 3, 46, 46, 5);
		gr.fillRoundRect(x + width/2 - 3 + 100, y + height/2 - 3, 46, 46, 5);
		
		gr.setColor(Color.white);
		gr.fillRoundRect(x + width/2 - 3 + 100, y + height/2 - 3 + 50, 46, 46, 5);
		
		gr.setColor(Color.darkGray);
		gr.fillRoundRect(x + width/2, y + height/2, 40, 40, 5);
		gr.fillRoundRect(x + width/2 + 50, y + height/2, 40, 40, 5);
		gr.fillRoundRect(x + width/2 + 100, y + height/2, 40, 40, 5);
		gr.fillRoundRect(x + width/2 + 100, y + height/2 + 50, 40, 40, 5);
		
		gr.drawLine(x + width/2 + 40, y + height/2 + 20, x + width/2 + 50, y + height/2 + 20);
		gr.drawLine(x + width/2 + 40 + 50, y + height/2 + 20, x + width/2 + 50 + 50, y + height/2 + 20);
		gr.drawLine(x + width/2 + 120, y + height/2 + 40, x + width/2 +	120, y + height/2 + 50);
		
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, float delta) {
		
	}

}
