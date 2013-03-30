package GUI;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import entities.players.abilities.PlayerAbility;
import game.config.Config;

public class AbilitiesWindow extends Window {
	
	private int numberOfAbilities = 6;
	private ArrayList<PlayerAbility> abilities  = new ArrayList<PlayerAbility>(numberOfAbilities);
	private int abilitySelected = 0;
	
	public AbilitiesWindow() {
		this.x = Config.getScreenWidth() * 0.25f;
		this.y = Config.getScreenHeight() * 0.25f;
		this.width = Config.getScreenWidth() * 0.5f;
		this.height = Config.getScreenHeight() * 0.5f;
	}

	@Override
	public void render(Graphics gr) {
		gr.setColor(new Color(0.5f, 0.5f, 0.5f, 0.8f));
		gr.fillRoundRect(x, y, width, height, 5);
		
		gr.setColor(Color.black);
		gr.drawString("Abilities", x + (width - gr.getFont().getWidth("Abilities")) * 0.5f , y + 10);
		
		gr.setColor(new Color(0.8f, 0, 0, 0.7f));
		gr.fillRoundRect(x + 15, y + 35 + abilitySelected*40, 40, 40, 5);
		
		for (int i = 0; i < 6; i++) {
			gr.setColor(Color.darkGray);
			gr.fillRoundRect(x + 20, y + 40 + i*40, 30, 30, 5);
			
			gr.setColor(Color.black);
			gr.drawString(new Integer(i + 1).toString(), x + 33, y + 45 + i*40);
			gr.drawString("Abilitiy name", x + 20 + 30 + 10, y + 45 + i*40);
		}
		
		gr.setColor(Color.darkGray);
		gr.fillRoundRect(x + width * 0.5f, y + 40, 100, 100, 5);
		
		gr.setColor(Color.black);
		gr.drawString(new Integer(abilitySelected + 1).toString(), x + width * 0.5f + 40 , y + 50);
		gr.drawString("Nice image for ability", x + width*0.5f + 50 - gr.getFont().getWidth("Nice image for ability")/2 , y + 80);
		gr.drawString("Ability description", x + width * 0.5f , y + 150);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, float delta) {
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_TAB)) {
			abilitySelected++;
			abilitySelected = abilitySelected % numberOfAbilities;
		}
		
		
	}


}
