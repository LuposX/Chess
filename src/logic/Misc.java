package logic;

import pieces.King;
import pieces.Piece;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Misc {
	/**
	 * Used for Methods which cant be assigned to other classed.
	 */

	/**
	 * Checks for a checkmate on the board
	 * @param board the chessboard
	 * @return returns true when there is a chckMate on the board
	 */
	public static boolean checkCheckMate(Cell[][] board)  {
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

	/**
	 * Checks if the King is in Check
	 * @return boolean[] first entry is for white king, second entry is for black king
	 */
	public static boolean[] checkCheck(Cell[][] board) {
		// in here we write the position of the kings
		Point whiteKingsPos = new Point();
		Point blackKingsPos = new Point();

		boolean[] kingInCheck = new boolean[2];

		// if the king got found on theb oard
		boolean blackKingFound = false;
		boolean whiteKingFound = false;

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

		// Check if any piece can move to the white king field
		if(whiteKingFound) {
			outerloop:
			for (int i = 0; i < board.length; i++) {
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
		}

		// Check if any piece can move to the black king field
		if(blackKingFound) {
			outerloop:
			for (int i = 0; i < board.length; i++) {
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
		}

		return kingInCheck;
	}


	/**
	 * Needs to be called in every child class in the methio is move valid. Used in the piece class
	 * @param board the chess board
	 * @return
	 */
	public static Boolean getCheckCheck(int curr_x, int curr_y, int int_x, int int_y, Cell[][] board, Piece startPiece, Piece endPiece) {

		// tmp change the board to the intended move
		board[int_y][int_x] = new Cell(int_y, int_x, board[curr_y][curr_x].getPiece());
		board[curr_y][curr_x] = new Cell(curr_y, curr_x, null);

		// Check if King is in check
		boolean[] checkKing = Misc.checkCheck(board);

		// tmp change the board back to the original
		board[curr_y][curr_x] = new Cell(curr_y, curr_x, board[int_y][int_x].getPiece());
		board[int_y][int_x] = new Cell(int_y, int_x,null);

		if (checkKing[0] || checkKing[1] ) {
			System.out.println("King is in check");
			return true;
		} else {
			return  false;
		}
	}

	/**
	 * Needs to be called in every child class in the methio is move valid
	 * @param board the chess board
	 * @return
	 */
	public static Boolean getCheckCheck(Cell[][] board) {
		// Check if King is in check
		boolean[] checkKing = Misc.checkCheck(board);
		if (checkKing[0] || checkKing[1] ) {
			System.out.println("King is in check");
			return true;
		} else {
			return  false;
		}
	}


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
	 * Deep copies a object so that changes to the copied object doesnt chaneg the values of original object
	 * @param object the object which should be clonsed
	 * @return deep copy of parameter object
	 */
	public static Object deepCopy(Object object) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream outputStrm = new ObjectOutputStream(outputStream);
			outputStrm.writeObject(object);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
			ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
			return objInputStream.readObject();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
