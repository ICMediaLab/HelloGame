package utils;

import org.newdawn.slick.Image;

/**
 * A collection of static methods for use in image manipulation.
 */
public class ImageUtils {
	
	/**
	 * Returns a new image flipped in the axis specified.<br />
	 * Does not affect the original image.
	 */
	public static Image flipImage(Image i, boolean xAxis, boolean yAxis){
		return i.getScaledCopy(
				xAxis ? -i.getWidth() : i.getWidth(), 
				yAxis ? -i.getHeight() : i.getHeight());		
	}
	
	/**
	 * Returns a new array of images flipped in the axis specified.<br />
	 * Does not affect the original image array.<br />
	 * Images will be in identical order to that passed.
	 */
	public static Image[] flipImages(Image[] is, boolean xAxis, boolean yAxis){
		Image[] res = new Image[is.length];
		for(int i=0;i<is.length;i++){
			res[i] = flipImage(is[i], xAxis, yAxis);
		}
		return res;
	}
}
