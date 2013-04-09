package utils;

import game.config.Config;

import org.newdawn.slick.Font;

public enum HorizontalAlign {
	LEFT(0f,1f,0f),CENTRE(0.5f,0f,-0.5f),RIGHT(1f,-1f,-1f);

	private final float widthMod, paddingMod, fontMod;
	
	private HorizontalAlign(float widthMod, float paddingMod, float fontMod) {
		this.widthMod = widthMod;
		this.paddingMod = paddingMod;
		this.fontMod = fontMod;
	}
	
	public float getX(float base, float width, float padding, Font font, String str){
		return Config.getTileSize()*(base + width*widthMod - 1) + padding*paddingMod + font.getWidth(str)*fontMod;
	}
	
	public static HorizontalAlign parseAlignment(String str, HorizontalAlign def){
		for(HorizontalAlign va : values()){
			if(va.toString().equalsIgnoreCase(str)){
				return va;
			}
		}
		return def;
	}
}