package utils;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public interface ImageContainer {

	ImageContainer flippedCopy(boolean h, boolean v);

	Animation getAnimation(int duration);
	
	int getSingleImageHeight();
	int getSingleImageWidth();
}

class ArrayImageContainer implements ImageContainer {
	
	private final Image[] images;
	
	public ArrayImageContainer(Image[] images) {
		this.images = images;
	}

	@Override
	public ImageContainer flippedCopy(boolean h, boolean v) {
		return new ArrayImageContainer(ImageUtils.flipImages(images, h, v));
	}

	@Override
	public Animation getAnimation(int duration) {
		return new Animation(images, duration);
	}

	@Override
	public int getSingleImageHeight() {
		return images[0].getHeight();
	}

	@Override
	public int getSingleImageWidth() {
		return images[0].getWidth();
	}
	
	
	
}

class SpriteImageContainer implements ImageContainer {
	
	private final SpriteSheet ss;
	
	public SpriteImageContainer(SpriteSheet ss) {
		this.ss = ss;
	}

	@Override
	public ImageContainer flippedCopy(boolean h, boolean v) {
		return new SpriteImageContainer(new SpriteSheet(ss.getFlippedCopy(h, v),getSingleImageWidth(),getSingleImageHeight()));
	}

	@Override
	public Animation getAnimation(int duration) {
		return new Animation(ss,duration);
	}

	@Override
	public int getSingleImageHeight() {
		return ss.getHeight()/ss.getVerticalCount();
	}

	@Override
	public int getSingleImageWidth() {
		return ss.getWidth()/ss.getHorizontalCount();
	}
	
	
	
}
