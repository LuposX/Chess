package pieces;

import logic.Cell;
import logic.Misc;

public class Queen extends Piece{

	public Queen(Boolean isWhite) {
		super(isWhite, "queen", 9);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean isValidPath(int curr_x, int curr_y, int int_x, int int_y, Cell[][] board, boolean checkCheck, Piece startPiece, Piece endPiece) {

		// Pieces of same color cant capture each other
		if(checkCaptureOwnPiece(curr_x, curr_y, int_x, int_y, board)) {
			return false;
		}

		// Check if King is in check and in checkMate
		if(checkCheck) {
			this.kingIsInCheck = Misc.getCheckCheck(curr_x, curr_y, int_x, int_y, board, startPiece, endPiece);
		}
		
		// Calculate difference between start and end position
		int differenceX = Math.abs(curr_x - int_x);
		int differenceY = Math.abs(curr_y - int_y);

		out:
		if (curr_x == int_x) {

			if(curr_y > int_y) {
				// Check that no piece is between the path
				for (int i = 1; i < differenceY; i++) {
					if (board[curr_y - i][curr_x].getPiece() != null) {
						this.isValidMoveBool = false;
						break out;
					}
				}
				return true;  // if no piece is in the way
			} else {
				// Check that no piece is between the path
				for (int i = 1; i < differenceY; i++) {
					if (board[curr_y + i][curr_x].getPiece() != null) {
						this.isValidMoveBool = false;
						break out;
					}
				}
				this.isValidMoveBool = true;  // if no piece is in the way
			}

			
		} else if (curr_y == int_y) {
			if(curr_x > int_x) {
				// Check that no piece is between the path
				for (int i = 1; i < differenceX; i++) {
					if (board[curr_y][curr_x - i].getPiece() != null) {
						this.isValidMoveBool = false;
						break out;
					}
				}
				this.isValidMoveBool = true;  // if no piece is in the way
			} else {
				// Check that no piece is between the path
				for (int i = 1; i < differenceX; i++) {
					if (board[curr_y][curr_x + i].getPiece() != null) {
						this.isValidMoveBool = false;
						break out;
					}
				}
				this.isValidMoveBool = true;  // if no piece is in the way
			}
			
		} else if (differenceX == differenceY) {

			// Direction from source: bottom right
			if(curr_x + differenceX == int_x && curr_y + differenceY == int_y) {

				// Check if a piece is blocking the path
				for (int i = 1; i < differenceX; i++) {
					if (board[curr_y + i][curr_x + i].getPiece() != null) {
						this.isValidMoveBool = false;
						break out;
					}
				}
				this.isValidMoveBool = true;

				// Direction from source: bottom left
			} else if (curr_x - differenceX == int_x && curr_y + differenceY == int_y) {

				// Check if a piece is blocking the path
				for (int i = 1; i < differenceX; i++) {
					if (board[curr_y + i][curr_x - i].getPiece() != null) {
						this.isValidMoveBool = false;
						break out;
					}
				}
				this.isValidMoveBool = true;

				// Direction from source: top right
			} else if (curr_x + differenceX == int_x && curr_y - differenceY == int_y) {

				// Check if a piece is blocking the path
				for (int i = 1; i < differenceX; i++) {
					if (board[curr_y - i][curr_x + i].getPiece() != null) {
						this.isValidMoveBool = false;
						break out;
					}
				}
				this.isValidMoveBool = true;

				// Direction from source: top left
			} else {

				// Check if a piece is blocking the path
				for (int i = 1; i < differenceX; i++) {
					if (board[curr_y - i][curr_x - i].getPiece() != null) {
						this.isValidMoveBool = false;
						break out;
					}
				}
				this.isValidMoveBool = true;

			}

		} else {
			this.isValidMoveBool = false;
		}

		return checkIfMoveOutOfCheck(curr_x, curr_y, int_x, int_y, board, checkCheck, startPiece, endPiece, kingIsInCheck, isValidMoveBool);
	}
}
