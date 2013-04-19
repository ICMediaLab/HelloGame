package utils;

import game.config.Config;

import org.newdawn.slick.Font;

public enum VerticalAlign {
	TOP(0f,1f,0f),MIDDLE(0.5f,0f,-0.5f),BOTTOM(1f,-1f,-1f);
	
	private final float heightMod, paddingMod, fontMod;
	
	private VerticalAlign(float heightMod, float paddingMod, float fontMod) {
		this.heightMod = heightMod;
		this.paddingMod = paddingMod;
		this.fontMod = fontMod;
	}
	
	public float getY(float base, float height, float padding, Font font, String str){
		return Config.getTileSize()*(base + height*heightMod - 1) + padding*paddingMod + font.getHeight(str)*fontMod;
	}
	
	public static VerticalAlign parseAlignment(String str, VerticalAlign def){
		try{
			return valueOf(str.toUpperCase());
		}catch(NullPointerException e){
			return def;
		}
	}
}