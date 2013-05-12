package GUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import entities.players.abilities.PlayerAbilities;

class AbilitiesWindow extends AbstractWindow {
	
	private static final long serialVersionUID = -4181119528378773217L;
	
	private int abilitySelected = 0;
	
	public AbilitiesWindow(GUI gui) {
		super(gui);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		float x = getX(), y = getY(), width = getWidth(), height = getHeight();
		g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.8f));
		g.fillRoundRect(x, y, width, height, 5);
		
		g.setColor(Color.black);
		g.drawString("Abilities", x + (width - g.getFont().getWidth("Abilities")) * 0.5f , y + 10);
		
		g.setColor(new Color(0.8f, 0, 0, 0.7f));
		g.fillRoundRect(x + 15, y + 35 + abilitySelected*40, 40, 40, 5);
		
		PlayerAbilities[] abilitiesList = PlayerAbilities.values();
		
		for (int i = 0; i < abilitiesList.length; i++) {
			g.setColor(Color.darkGray);
			g.fillRoundRect(x + 20, y + 40 + i*40, 30, 30, 5);
			abilitiesList[i].getImage().draw(x + 20, y + 40 + i*40, 0.3f);
			
			g.setColor(Color.black);
			g.drawString(new Integer(i + 1).toString(), x + 33, y + 45 + i*40);
			g.drawString(abilitiesList[i].getName(), x + 20 + 30 + 10, y + 45 + i*40);
		}
		
		g.setColor(Color.darkGray);
		g.fillRoundRect(x + width * 0.5f, y + 40, 100, 100, 5);
		abilitiesList[abilitySelected].getImage().draw(x + width * 0.5f, y + 40, 1);
		
		g.setColor(Color.black);
		g.drawString(new Integer(abilitySelected + 1).toString(), x + width * 0.5f + 40 , y + 50);
		g.drawString("Nice image for ability", x + width*0.5f + 50 - g.getFont().getWidth("Nice image for ability")/2 , y + 80);
		g.drawString(abilitiesList[abilitySelected].getDescription(), x + width * 0.5f , y + 150);
		
	}

	@Override
	public void update(GameContainer gc) {
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_TAB)) {
			abilitySelected ++;
			abilitySelected %= PlayerAbilities.values().length;
		}
		
	}


}
