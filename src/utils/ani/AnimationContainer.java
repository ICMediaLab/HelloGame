package utils.ani;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import utils.ImageUtils;
import utils.Position;

public class AnimationContainer {

	private final Animation base;
	private final ImageContainer images;
	private final Position offset;
	
	private AnimationContainer(Animation base, ImageContainer images, Position offset){
		this.base = base;
		this.images = images;
		this.offset = offset;
		this.base.setAutoUpdate(false);
	}
	
	public AnimationContainer(Image[] base, int duration, Position offset){
		this(new Animation(base,duration),new ArrayImageContainer(base),offset);
	}
	
	public AnimationContainer(SpriteSheet base, int duration, Position offset){
		this(new Animation(base,duration),new SpriteImageContainer(base),offset);
	}
	
	public AnimationContainer(ImageContainer base, int duration, Position offset){
		this(base.getAnimation(duration),base,offset);
	}
	
	public AnimationContainer(Node node) throws SlickException {
		NamedNodeMap attrs = node.getAttributes();
		
		Image[] imgs = ImageUtils.loadImages(node);
		
		int duration = Integer.parseInt(attrs.getNamedItem("duration").getTextContent());
		boolean split = false;
		try{
			split = Boolean.parseBoolean(attrs.getNamedItem("sliced").getTextContent());
		}catch(NullPointerException e){ }
		if(split){
			int width = Integer.parseInt(attrs.getNamedItem("width").getTextContent());
			int height = imgs[0].getHeight();
			SpriteSheet ss = new SpriteSheet(imgs[0], width, height);
			this.images = new SpriteImageContainer(ss);
			this.base = new Animation(ss, duration);
		}else{
			this.images = new ArrayImageContainer(imgs);
			this.base = new Animation(imgs, duration);
		}
		float offsetX = 16f - images.getSingleImageWidth()/2f;
		float offsetY = 32f - images.getSingleImageHeight();
		try {
			offsetX = Float.parseFloat(attrs.getNamedItem("offsetX").getTextContent());
		}catch(NullPointerException e){ }
		try {
			offsetY = Float.parseFloat(attrs.getNamedItem("offsetY").getTextContent());
		}catch(NullPointerException e){ }
		try {
			offsetX += Float.parseFloat(attrs.getNamedItem("offsetDispX").getTextContent());
		}catch(NullPointerException e){ }
		try {
			offsetY += Float.parseFloat(attrs.getNamedItem("offsetDispY").getTextContent());
		}catch(NullPointerException e){ }
		offset = new Position(offsetX, offsetY);
		this.base.setAutoUpdate(false);
	}

	public Animation getAnimation(){
		return base;
	}
	
	public ImageContainer getImages(){
		return images;
	}
	
	public Position getOffset(){
		return offset;
	}
	
	public int getDuration(){
		return base.getDuration(0);
	}

	public AnimationContainer flippedCopy(boolean horizontal, boolean vertical) {
		return new AnimationContainer(ImageUtils.flipImages(getImages(),horizontal,vertical),getDuration(),getOffset());
	}

	public void update(long delta) {
		base.update(delta);
	}
	
}
