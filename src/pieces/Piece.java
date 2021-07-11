package pieces;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import logic.Board;
import logic.Cell;
import logic.Misc;
import logic.possibleKingCheckSpots;

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
	 * Needs to be called in every child class in the methio is move valid
	 * @param board the chess board
	 * @return
	 */
	public Boolean getCheckCheck(Cell[][] board) {
		// Check if King is in check
		boolean[] checkKing = checkCheck(board);
		if (checkKing[0] || checkKing[1] ) {
			System.out.println("King is in check");
			 return true;
		} else {
			return  false;
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
	public boolean checkIfMoveOutOfCheck(int curr_x, int curr_y, int int_x, int int_y, Cell[][] board, boolean checkCheck, Piece startPiece, Piece endPiece, boolean kingIsInCheck, boolean isValidMoveBool) {
		// When the move is valid
		if(isValidMoveBool) {

			// When checking check is turned on, Check if new move makes king out of check
			if(checkCheck) {

				// create a tmp chessboard with the new move on it in order to check if the new move moves the king out of check or blocks the check
				if(kingIsInCheck) {

					// Change the values of the copy to the intented move
					board[int_y][int_x] = new Cell(int_y, int_x, board[curr_y][curr_x].getPiece());
					board[curr_y][curr_x] = new Cell(curr_y, curr_x, null);

					boolean[] checkKing = checkCheck(board);
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


	/**
	 * Checks if the King is in Check
	 * @return boolean[] first entry is for white king, second entry is for black king
	 */
	private boolean[] checkCheck(Cell[][] board) {
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

		// Check if any piece can move to the white king field
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

		// Check if any piece can move to the black king field
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

	/**
	 * Checks for a checkmate on the board
	 * @param board the chessboard
	 * @return returns true when there is a chckMate on the board
	 */
	public boolean checkCheckMate(Cell[][] board)  {
		// in here we write the position of the kings
		Point whiteKingsPos = new Point();
		Point blackKingsPos = new Point();

		// if the king got found on theb oard
		boolean blackKingFound = false;
		boolean whiteKingFound = false;

		//Point[] movesWhereKingCouldMoveTo = new Point[8];
		ArrayList<possibleKingCheckSpots> movesWhereKingCouldMoveToW = new ArrayList<possibleKingCheckSpots>();
		ArrayList<possibleKingCheckSpots> movesWhereKingCouldMoveToB = new ArrayList<possibleKingCheckSpots>();

		boolean[] kingInCheck = new boolean[2];

		boolean freeSpot;

		// Get position of King
		for(int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {

				// Check that the piece is a king
				if (board[i][j].getPiece() instanceof King) {

					// Check which color the king piece has
					if(board[i][j].getPiece().getIsWhite()) {
						whiteKingsPos = new Point(board[i][j].getX(), board[i][j].getY());
						whiteKingFound = true;
					} else {
						blackKingsPos = new Point(board[i][j].getX(), board[i][j].getY());
						blackKingFound = true;
					}
				}
			}
		}

		// Check if any piece can move to the king field
		if(blackKingFound) {

			// All the moves the King could move to
			// the "?" is used for conidtion: Variable = Condition ? BlockTrue : BlockElse;
			// Used so that we dont go outside of the chess board
			movesWhereKingCouldMoveToB.clear(); // clear the array
			if(blackKingsPos.y + 1 < 7) {
				movesWhereKingCouldMoveToB.add(new possibleKingCheckSpots(new Point(blackKingsPos.x, blackKingsPos.y + 1)));
			}
			if(blackKingsPos.y - 1 > 0) {
				movesWhereKingCouldMoveToB.add(new possibleKingCheckSpots(new Point(blackKingsPos.x, blackKingsPos.y - 1)));
			}
			if(blackKingsPos.x + 1 < 7) {
				movesWhereKingCouldMoveToB.add(new possibleKingCheckSpots(new Point(blackKingsPos.x + 1, blackKingsPos.y)));
			}
			if(blackKingsPos.x - 1 > 0) {
				movesWhereKingCouldMoveToB.add(new possibleKingCheckSpots(new Point(blackKingsPos.x - 1, blackKingsPos.y)));
			}
			if(blackKingsPos.x + 1 < 7 && blackKingsPos.y + 1 < 7) {
				movesWhereKingCouldMoveToB.add(new possibleKingCheckSpots(new Point(blackKingsPos.x + 1, blackKingsPos.y + 1)));
			}
			if(blackKingsPos.x - 1 > 0 && blackKingsPos.y - 1 > 0) {
				movesWhereKingCouldMoveToB.add(new possibleKingCheckSpots(new Point(blackKingsPos.x - 1, blackKingsPos.y - 1)));
			}
			if(blackKingsPos.x + 1 < 7 && blackKingsPos.y - 1 > 0) {
				movesWhereKingCouldMoveToB.add(new possibleKingCheckSpots(new Point(blackKingsPos.x + 1, blackKingsPos.y - 1)));
			}
			if(blackKingsPos.x - 1 > 0 && blackKingsPos.y + 1 < 7) {
				movesWhereKingCouldMoveToB.add(new possibleKingCheckSpots(new Point(blackKingsPos.x - 1, blackKingsPos.y + 1)));
			}

			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {

					if (board[i][j].getPiece() != null) {

						Piece startPiece = board[i][j].getPiece(); // The Piece from where we start

						// Check if the King can move to any spot
						for (int k = 0; k < movesWhereKingCouldMoveToB.size(); k++) {

							Piece endPiece = board[movesWhereKingCouldMoveToB.get(k).getPoint().y][movesWhereKingCouldMoveToB.get(k).getPoint().x].getPiece(); // The Piece where we end up

							// only if we have a enemy piece do we check if it can reach the new king position
							if(!startPiece.getIsWhite()) {

								// update the board temporarily with the king movement
								board[blackKingsPos.y][blackKingsPos.x] = new Cell(blackKingsPos.y, blackKingsPos.x, null);
								board[movesWhereKingCouldMoveToW.get(k).getPoint().y][movesWhereKingCouldMoveToW.get(k).getPoint().x] = new Cell(movesWhereKingCouldMoveToW.get(k).getPoint().y, movesWhereKingCouldMoveToW.get(k).getPoint().x, new King(false));

								// Check if valid
								freeSpot = board[i][j].getPiece().isValidPath(board[i][j].getX(), board[i][j].getY(), movesWhereKingCouldMoveToW.get(k).getPoint().x, movesWhereKingCouldMoveToW.get(k).getPoint().y, board, false, startPiece, endPiece);

								// reset board from king movement
								board[blackKingsPos.y][blackKingsPos.x] = new Cell(blackKingsPos.y, blackKingsPos.x, new King(false));
								board[movesWhereKingCouldMoveToW.get(k).getPoint().y][movesWhereKingCouldMoveToW.get(k).getPoint().x] = new Cell(movesWhereKingCouldMoveToW.get(k).getPoint().y, movesWhereKingCouldMoveToW.get(k).getPoint().x, null);

								if(!movesWhereKingCouldMoveToW.get(k).getIsPointCovered()) {
									movesWhereKingCouldMoveToW.get(k).setPointCovered(freeSpot);
								}
							}


						}

					}
				}
			}

			for(int m = 0; m < movesWhereKingCouldMoveToB.size(); m++) {
				if(!movesWhereKingCouldMoveToB.get(m).getIsPointCovered()) {
					return false;
				}
			}

		}


		if(whiteKingFound) {

			// All the moves the King could move to
			// the "?" is used for conidtion: Variable = Condition ? BlockTrue : BlockElse;
			// Used so that we dont go outside of the chess board
			movesWhereKingCouldMoveToW.clear(); // clear the array
			if(whiteKingsPos.y + 1 < 7) {
				movesWhereKingCouldMoveToW.add(new possibleKingCheckSpots(new Point(whiteKingsPos.x, whiteKingsPos.y + 1)));
			}
			if(whiteKingsPos.y - 1 > 0) {
				movesWhereKingCouldMoveToW.add(new possibleKingCheckSpots(new Point(whiteKingsPos.x, whiteKingsPos.y - 1)));
			}
			if(whiteKingsPos.x + 1 < 7) {
				movesWhereKingCouldMoveToW.add(new possibleKingCheckSpots(new Point(whiteKingsPos.x + 1, whiteKingsPos.y)));
			}
			if(whiteKingsPos.x - 1 > 0) {
				movesWhereKingCouldMoveToW.add(new possibleKingCheckSpots(new Point(whiteKingsPos.x - 1, whiteKingsPos.y)));
			}
			if(whiteKingsPos.x + 1 < 7 && whiteKingsPos.y + 1 < 7) {
				movesWhereKingCouldMoveToW.add(new possibleKingCheckSpots(new Point(whiteKingsPos.x + 1, whiteKingsPos.y + 1)));
			}
			if(whiteKingsPos.x - 1 > 0 && whiteKingsPos.y - 1 > 0) {
				movesWhereKingCouldMoveToW.add(new possibleKingCheckSpots(new Point(whiteKingsPos.x - 1, whiteKingsPos.y - 1)));
			}
			if(whiteKingsPos.x + 1 < 7 && whiteKingsPos.y - 1 > 0) {
				movesWhereKingCouldMoveToW.add(new possibleKingCheckSpots(new Point(whiteKingsPos.x + 1, whiteKingsPos.y - 1)));
			}
			if(whiteKingsPos.x - 1 > 0 && whiteKingsPos.y + 1 < 7) {
				movesWhereKingCouldMoveToW.add(new possibleKingCheckSpots(new Point(whiteKingsPos.x - 1, whiteKingsPos.y + 1)));
			}

			// Check if any piece can move to the king field
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {

					if (board[i][j].getPiece() != null) {

						Piece startPiece = board[i][j].getPiece(); // The Piece from where we start

						// Check if the King can move to any spot
						for (int k = 0; k < movesWhereKingCouldMoveToW.size(); k++) {

							Piece endPiece = board[movesWhereKingCouldMoveToW.get(k).getPoint().y][movesWhereKingCouldMoveToW.get(k).getPoint().x].getPiece(); // The Piece where we end up

							// Check that its a spot which is free and not where a other piece is
							if (endPiece == null) {

								// only if we have a enemy piece do we check if it can reach the new king position
								if(!startPiece.getIsWhite()) {

									// update the board temporarily with the king movement
									board[whiteKingsPos.y][whiteKingsPos.x] = new Cell(whiteKingsPos.y, whiteKingsPos.x, null);
									board[movesWhereKingCouldMoveToW.get(k).getPoint().y][movesWhereKingCouldMoveToW.get(k).getPoint().x] = new Cell(movesWhereKingCouldMoveToW.get(k).getPoint().y, movesWhereKingCouldMoveToW.get(k).getPoint().x, new King(true));

									// Check if valid
									freeSpot = board[i][j].getPiece().isValidPath(board[i][j].getX(), board[i][j].getY(), movesWhereKingCouldMoveToW.get(k).getPoint().x, movesWhereKingCouldMoveToW.get(k).getPoint().y, board, false, startPiece, endPiece);

									// reset board from king movement
									board[whiteKingsPos.y][whiteKingsPos.x] = new Cell(whiteKingsPos.y, whiteKingsPos.x, new King(true));
									board[movesWhereKingCouldMoveToW.get(k).getPoint().y][movesWhereKingCouldMoveToW.get(k).getPoint().x] = new Cell(movesWhereKingCouldMoveToW.get(k).getPoint().y, movesWhereKingCouldMoveToW.get(k).getPoint().x, null);

									if(!movesWhereKingCouldMoveToW.get(k).getIsPointCovered()) {
										movesWhereKingCouldMoveToW.get(k).setPointCovered(freeSpot);
									}
								}
							}
						}

					}
				}
			}

			for(int i = 0; i < movesWhereKingCouldMoveToW.size(); i++) {
				if(!movesWhereKingCouldMoveToW.get(i).getIsPointCovered()) {
					return false;
				}
			}

		}
		return true; // ???????? should never happen
	}


	public Boolean getIsWhite() {
		return this.isWhite;
	}
	
	public BufferedImage getImage() {
		return img;
	}
	
}
