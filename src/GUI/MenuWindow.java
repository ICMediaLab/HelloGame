package GUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import utils.mouse.MouseContainer;

class MenuWindow extends AbstractWindow {
	
	private final Button res;
	private final Button opt;
	private final Button exit;
	
	private boolean halt = false;
	
	public MenuWindow(GUI gui) {
		super(gui);
		float width = 160, height=60, cornerRadius = 5;
		float x = getX() + (getWidth() - width)*0.5f;
		res  = new Button("Resume",x, getY() + 30, width, height, cornerRadius);
		opt  = new Button("Options",x, getY() + 100, width, height, cornerRadius);
		exit = new Button("Exit Game",x, getY() + 170, width, height, cornerRadius);
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		float x = getX(), y = getY(), width = getWidth(), height = getHeight();
		g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.8f));
		g.fillRoundRect(x, y, width, height, 5);
		
		res.render( g, Color.darkGray, Color.black);
		opt.render( g, Color.darkGray, Color.black);
		exit.render(g, Color.darkGray, Color.black);
	}

	@Override
	public void update(GameContainer gc) {
		if(halt){
			gc.exit();
		}
	}
	
	@Override
	public void mouseReleased(MouseContainer mc) {
		if(res.contains(mc.getX(), mc.getY())){
			getGUI().closeWindow();
		}else if(opt.contains(mc.getX(), mc.getY())){
			getGUI().setActiveWindow(Window.OPTIONS);
		}else if(exit.contains(mc.getX(), mc.getY())){
			halt = true;
		}
	}

}
