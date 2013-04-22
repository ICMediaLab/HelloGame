package GUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;

import sounds.Sounds;
import utils.mouse.MouseContainer;

class MenuWindow extends AbstractWindow {
	
	private static final long serialVersionUID = -4965718012178473738L;
	
	private final Button res;
	private final Button opt;
	private final Button exit;
	
	private boolean halt = false;
	
	private Sound click;
	
	public MenuWindow(GUI gui) {
		super(gui);
		float width = 160, height=60, cornerRadius = 5;
		float x = getX() + (getWidth() - width)*0.5f;
		res  = new Button("Resume",x, getY() + 30, width, height, cornerRadius);
		opt  = new Button("Options",x, getY() + 100, width, height, cornerRadius);
		exit = new Button("Exit Game",x, getY() + 170, width, height, cornerRadius);
		
		this.click = Sounds.loadSound("gui/menu_select.wav");
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		float x = getX(), y = getY(), width = getWidth(), height = getHeight();
		g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.8f));
		g.fillRoundRect(x, y, width, height, 5);
		res.render(g);
		opt.render(g);
		exit.render(g);
	}

	@Override
	public void update(GameContainer gc) {
		if(halt){
			gc.exit();
		}
	}
	
	@Override
	public void mouseReleased(MouseContainer mc) {
		if(res.contains(mc)){
			getGUI().closeWindow();
			Sounds.play(click); 
		}else if(opt.contains(mc)){
			getGUI().setActiveWindow(Window.OPTIONS);
			Sounds.play(click); 
		}else if(exit.contains(mc)){
			halt = true;
			Sounds.play(click); 
		}
	}

}
