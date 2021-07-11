package pieces;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import logic.*;

/**
 * 
 */
public abstract class Piece {
	protected Boolean isWhite;
	private BufferedImage img;
	private String imagePath;
	private final Integer PIECE_VALUE;
	protected boolean kingIsInCheck;
	protected boolean isValidMoveBool;

	public Piece(Boolean isWhite, String pieceName, int PIECE_VALUE){
		this.PIECE_VALUE = PIECE_VALUE;
		this.isWhite = isWhite;
		this.kingIsInCheck = false;

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
	 * @param checkCheck if true the method check if the king is in check if false method does not check if king is in check
	 * @return Bolean if the path is valid return true else false
	 */
	public abstract Boolean isValidPath(int curr_x, int curr_y, int int_x, int int_y, Cell[][] board, boolean checkCheck, Piece startPiece, Piece endPiece, Player playerTurn);


	/**
	 * Pieces of same color cant capture each other
	 * @param curr_x current x cordinate of the piece
	 * @param curr_y current y cordinate of the piece
	 * @param int_x intented x
	 * @param int_y intented y
	 * @param chessBoard is the chessBoard needed to check if you capture your own piece
	 * @return Bolean returns true when you try to capture your own piece
	 */
	public boolean checkCaptureOwnPiece(int curr_x, int curr_y, int int_x, int int_y, Cell[][] chessBoard) {
		if (chessBoard[curr_y][curr_x].getPiece() != null && chessBoard[int_y][int_x].getPiece() != null) {
			if (chessBoard[curr_y][curr_x].getPiece().getIsWhite() == chessBoard[int_y][int_x].getPiece().getIsWhite()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}


	/**
	 * @param curr_x
	 * @param curr_y
	 * @param int_x
	 * @param int_y
	 * @param board
	 * @param checkCheck
	 * @param startPiece
	 * @param endPiece
	 * @param kingIsInCheck
	 * @param isValidMoveBool
	 * @return
	 */
	public boolean checkIfMoveOutOfCheck(int curr_x, int curr_y, int int_x, int int_y, Cell[][] board, boolean checkCheck, Piece startPiece, Piece endPiece, boolean kingIsInCheck, boolean isValidMoveBool, Player playerTurn) {
		// When the move is valid
		if(isValidMoveBool) {

			// When checking check is turned on, Check if new move makes king out of check
			if(checkCheck) {

				// create a tmp chessboard with the new move on it in order to check if the new move moves the king out of check or blocks the check
				if(kingIsInCheck) {

					// Change the values of the copy to the intented move
					board[int_y][int_x] = new Cell(int_y, int_x, board[curr_y][curr_x].getPiece());
					board[curr_y][curr_x] = new Cell(curr_y, curr_x, null);

					boolean[] checkKing = Misc.checkCheck(board, playerTurn);
					if (checkKing[0] || checkKing[1] ) {
						System.out.println("Move doesnt bring king out of check");

						// Change the values back to the original
						board[curr_y][curr_x] = new Cell(curr_y, curr_x, startPiece);
						board[int_y][int_x] = new Cell(int_y, int_x, endPiece);

						return false;
					} else {
						return true;
					}

				} else {
					return true;
				}

				// When the move is valid and checks are turned off
			} else {
				return true;
			}

			// When move is no valid
		} else {
			return false;
		}
	}


	public Boolean getIsWhite() {
		return this.isWhite;
	}
	
	public BufferedImage getImage() {
		return img;
	}
	
}
