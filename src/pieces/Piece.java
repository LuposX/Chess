package pieces;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import logic.Board;
import logic.Cell;
import logic.Misc;

/**
 * 
 */
public abstract class Piece {
	private Boolean isWhite;
	private BufferedImage img;
	private String imagePath;
	private final Integer PIECE_VALUE;
	
	public Piece(Boolean isWhite, String pieceName, int PIECE_VALUE){
		this.PIECE_VALUE = PIECE_VALUE;
		this.isWhite = isWhite;
		
		try {
			// If its a white piece we use white in the image name, else we use black to indicate which image to use
			if (this.isWhite) {
				this.imagePath = "res/piecesImg/" + pieceName + "_white.png";
			} else {
				this.imagePath = "res/piecesImg/" + pieceName + "_black_rotated.png";
			}
			img = ImageIO.read(getClass().getClassLoader().getResourceAsStream(this.imagePath));
			img = Misc.resize(img, 800, 800);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param curr_x current x cordinate of the piece
	 * @param curr_y current y cordinate of the piece
	 * @param int_x intented x
	 * @param int_y intented y
	 * @param board is the chessBoard needed to check for thing like if a piece intercepets another
	 * @return Bolean if the path is valid return true else false
	 */
	public abstract Boolean isValidPath(int curr_x, int curr_y, int int_x, int int_y, Cell[][] board);
	
	public Boolean getIsWhite() {
		return this.isWhite;
	}
	
	public BufferedImage getImage() {
		return img;
	}
	
}
