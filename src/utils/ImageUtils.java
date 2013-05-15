package utils;

import java.util.Collection;
import java.util.List;

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
		return i.getFlippedCopy(xAxis, yAxis);		
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
	 * Returns a new array of images flipped in the axis specified.<br />
	 * Does not affect the original image array.<br />
	 * Images will be in identical order to that passed.
	 */
	public static Image[] flipImages(List<Image> is, boolean xAxis, boolean yAxis){
		Image[] res = new Image[is.size()];
		int i=0;
		for(Image img : is){
			res[i++] = flipImage(img, xAxis, yAxis);
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
	
	public static ImageContainer flipImages(ImageContainer images, boolean h, boolean v) {
		return images.flippedCopy(h,v);
	}
	
	/**
	 * Populates the container specified with the images specified.
	 * @return The modified container passed initially.
	 */
	public static Collection<Image> populate(Collection<Image> col, Image... images) {
		for(Image i : images){
			col.add(i);
		}
		return col;
	}
	
	/**
	 * Populates the container specified with images loaded from the paths specified.
	 * @return The modified container passed initially. 
	 */
	public static Collection<Image> populate(Collection<Image> col, String... paths) {
		return populate(col, populate(paths));
	}
	
	/**
	 * Populates a new array with images loaded from the paths specified.
	 * @return A new array of size equal to the number of paths specified, containing references to all images loaded.
	 */
	public static Image[] populate(String... paths){
		Image[] images = new Image[paths.length];
		for(int i=0;i<paths.length;i++){
			try {
				images[i] = new Image(paths[i]);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		return images;
	}
	
	public static <T> void appendReverse(List<? super T> xsBase, T[] xsArray) {
		for(int i=xsArray.length-1;i>=0;i--){
			xsBase.add(xsArray[i]);
		}
	}
	
}
