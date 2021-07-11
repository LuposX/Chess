package pieces;

import logic.Cell;
import logic.Misc;

public class Bishop extends Piece{

	public Bishop(Boolean isWhite) {
		super(isWhite, "bishop", 3);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean isValidPath(int curr_x, int curr_y, int int_x, int int_y, Cell[][] board, boolean checkCheck, Piece startPiece, Piece endPiece) {

		// Calculate difference between start and end position
		int differenceX = Math.abs(curr_x - int_x);
		int differenceY = Math.abs(curr_y - int_y);

		// Check if King is in check and in checkMate
		if(checkCheck) {
			this.kingIsInCheck = Misc.getCheckCheck(curr_x, curr_y, int_x, int_y, board, startPiece, endPiece);
		}

		// Pieces of same color cant capture each other
		if(checkCaptureOwnPiece(curr_x, curr_y, int_x, int_y, board)) {
			return false;
		}

		if(differenceX == differenceY) {

			// Direction from source: bottom right
			out:
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
		return checkIfMoveOutOfCheck(curr_x, curr_y, int_x, int_y, board, checkCheck, startPiece, endPiece, this.kingIsInCheck, this.isValidMoveBool);
	}
}
