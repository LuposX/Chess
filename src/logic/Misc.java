package logic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Misc {
	/**
	 * Used for Methods which cant be assigned to other classed.
	 */
	
	/**
	 * Resizes a BufferedImage. 
	 * Copied From StackOverflow: @Ocracoke .
	 * @param img	The Image one wishes to resize
	 * @param newW  The new width the Image should have
	 * @param newH  The new Height the Image should have
	 * @return	The new resized Image
	 */
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}  
	
	/**
	 * Used to check if an array is full/contains any null elements
	 * @param arrayP a in 2d array which should be checked
	 * @return return true if the every element is full/every element is not 0
	 */
	public static Boolean isArrayFull(Integer[][] arrayP) {
		for (int i = 0; i < arrayP.length; i++) {
			for (int j = 0; j < arrayP[i].length; j++) {
				
				if (arrayP[i][j] == null) {
					return false;
				}
				
			}
		}
		return true;
	}
}
