package pieces;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import logic.Board;
import logic.Cell;
import logic.Misc;

/**
 * 
 */
public abstract class Piece implements Serializable  {
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
	public abstract Boolean isValidPath(int curr_x, int curr_y, int int_x, int int_y, Cell[][] board, boolean checkCheck, Piece startPiece, Piece endPiece);


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
		if (chessBoard[int_y][int_x].getPiece() != null) {
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
	 * Checks if the King is in Check
	 * @return boolean[] first entry is for white king, second entry is for black king
	 */
	public boolean[] checkCheck(Cell[][] board) {
		// in here we write the position of the kings
		Point whiteKingsPos = new Point();
		Point blackKingsPos = new Point();

		boolean[] kingInCheck = new boolean[2];

		// Get position of King
		for(int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {

				// Check that the piece is a king
				if (board[i][j].getPiece() instanceof King) {

					// Check which color the king piece has
					if(board[i][j].getPiece().getIsWhite()) {
						whiteKingsPos = new Point(board[i][j].getX(), board[i][j].getY());
					} else {
						blackKingsPos = new Point(board[i][j].getX(), board[i][j].getY());
					}
				}
			}
		}

		// Check if any piece can move to the king field
		outerloop:
		for(int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {

				if (board[i][j].getPiece() != null) {

					Piece startPiece = board[i][j].getPiece(); // The Piece from where we start
					Piece endPiece = board[whiteKingsPos.y][whiteKingsPos.x].getPiece(); // The Piece where we end up

					kingInCheck[0] = board[i][j].getPiece().isValidPath(board[i][j].getX(), board[i][j].getY(), whiteKingsPos.x, whiteKingsPos.y, board, false, startPiece, endPiece);

					if (kingInCheck[0]) {
						break outerloop;
					}
				}
			}
		}

		// Check if any piece can move to the king field
		outerloop:
		for(int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {

				if (board[i][j].getPiece() != null) {

					Piece startPiece = board[i][j].getPiece(); // The Piece from where we start
					Piece endPiece = board[blackKingsPos.y][blackKingsPos.x].getPiece(); // The Piece where we end up

					kingInCheck[1] = board[i][j].getPiece().isValidPath(board[i][j].getX(), board[i][j].getY(), blackKingsPos.x, blackKingsPos.y, board, false, startPiece, endPiece);

					if (kingInCheck[1]) {
						break outerloop;
					}
				}
			}
		}

		return kingInCheck;
	}

	public Boolean getIsWhite() {
		return this.isWhite;
	}
	
	public BufferedImage getImage() {
		return img;
	}
	
}
