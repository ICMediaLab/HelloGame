package GUI;

import java.util.ArrayList;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import entities.players.abilities.AbilityFinder;
import entities.players.abilities.IPlayerAbility;
import entities.players.abilities.PlayerAbility;

class AbilitiesWindow extends AbstractWindow {
	
	private int numberOfAbilities = 4;
	private Map<String, IPlayerAbility> abilities = AbilityFinder.initialiseAbilities();
	private ArrayList<PlayerAbility> abilitiesList = new ArrayList<PlayerAbility>(numberOfAbilities);
	private int abilitySelected = 0;
	
	public AbilitiesWindow(GUI gui) {
		super(gui);
		abilitiesList.add((PlayerAbility) abilities.get("rangedattack"));
		abilitiesList.add((PlayerAbility) abilities.get("doublejump"));
		abilitiesList.add((PlayerAbility) abilities.get("speeddash"));
		abilitiesList.add((PlayerAbility) abilities.get("forwardteleport"));
	}
	
	@Override
	public void render(Graphics gr) {
		float x = getX(), y = getY(), width = getWidth(), height = getHeight();
		gr.setColor(new Color(0.5f, 0.5f, 0.5f, 0.8f));
		gr.fillRoundRect(x, y, width, height, 5);
		
		gr.setColor(Color.black);
		gr.drawString("Abilities", x + (width - gr.getFont().getWidth("Abilities")) * 0.5f , y + 10);
		
		gr.setColor(new Color(0.8f, 0, 0, 0.7f));
		gr.fillRoundRect(x + 15, y + 35 + abilitySelected*40, 40, 40, 5);
		
		for (int i = 0; i < abilitiesList.size(); i++) {
			gr.setColor(Color.darkGray);
			gr.fillRoundRect(x + 20, y + 40 + i*40, 30, 30, 5);
			abilitiesList.get(i).getImage().draw(x + 20, y + 40 + i*40, 0.3f);
			
			gr.setColor(Color.black);
			gr.drawString(new Integer(i + 1).toString(), x + 33, y + 45 + i*40);
			gr.drawString(abilitiesList.get(i).getName(), x + 20 + 30 + 10, y + 45 + i*40);
		}
		
		gr.setColor(Color.darkGray);
		gr.fillRoundRect(x + width * 0.5f, y + 40, 100, 100, 5);
		abilitiesList.get(abilitySelected).getImage().draw(x + width * 0.5f, y + 40, 1);
		
		gr.setColor(Color.black);
		gr.drawString(new Integer(abilitySelected + 1).toString(), x + width * 0.5f + 40 , y + 50);
		gr.drawString("Nice image for ability", x + width*0.5f + 50 - gr.getFont().getWidth("Nice image for ability")/2 , y + 80);
		gr.drawString(abilitiesList.get(abilitySelected).getDescription(), x + width * 0.5f , y + 150);
		
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
