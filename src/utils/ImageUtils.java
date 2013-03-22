package utils;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Node;

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
	
	/**
	 * Returns an array of {@link Image} objects as specified by the paths given.<br />
	 * Images are returned in identical order to that provided.
	 * @throws SlickException If any of the paths do not lead to a valid image.
	 */
	public static Image[] loadImages(String[] paths) throws SlickException{
		Image[] res = new Image[paths.length];
		for(int i=0;i<res.length;i++){
			res[i] = new Image(paths[i]);
		}
		return res;
	}
	
	/**
	 * Returns an array of {@link Image} objects as specified by the paths given.<br />
	 * Images are returned in identical order to that provided.
	 * @throws SlickException If any of the paths do not lead to a valid image.
	 */
	public static Image[] loadImages(Node node) throws SlickException{
		return loadImages(node.getTextContent().trim().split("\\s+"));
	}
}
