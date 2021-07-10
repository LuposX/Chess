package pieces;

import logic.Board;
import logic.Cell;
import logic.Misc;

public class Pawn extends Piece {
	private Boolean firstMove = true;

	public Pawn(Boolean isWhite) {
		super(isWhite, "pawn", 1);

		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean isValidPath(int curr_x, int curr_y, int int_x, int int_y, Cell[][] board, boolean checkCheck, Piece startPiece, Piece endPiece) {

		// Pieces of same color cant capture each other
		if(checkCaptureOwnPiece(curr_x, curr_y, int_x, int_y, board)) {
			return false;
		}

		// Check if King is in check
		if(checkCheck) {
			boolean[] checkKing = checkCheck(board);
			if (checkKing[0] || checkKing[1] ) {
				System.out.println("King is in check");
				this.kingIsInCheck = true;
			} else {
				this.kingIsInCheck = false;
			}
		}

		// We do -2 because y-axis starts at the top and goes down
		if((curr_x == int_x) && ((curr_y - 2)) == int_y && (this.firstMove) && this.isWhite) {
			this.firstMove = false;
			this.isValidMoveBool = true;
			
		} else if (curr_x == int_x && (curr_y - 1) == int_y && this.isWhite) {
			this.firstMove = false;
			this.isValidMoveBool = true;
			
		} else if (curr_x == int_x && (curr_y + 1) == int_y && !this.isWhite) {
			this.firstMove = false;
			this.isValidMoveBool = true;
			
		} else if ((curr_x == int_x) && ((curr_y + 2)) == int_y && (this.firstMove) && !this.isWhite) {
			this.firstMove = false;
			this.isValidMoveBool = true;
			
		} else if ((curr_x + 1 == int_x || curr_x - 1 == int_x) && curr_y - 1 == int_y && this.isWhite) {
			this.firstMove = false;
			this.isValidMoveBool = true;

		} else if ((curr_x + 1 == int_x || curr_x - 1 == int_x) && curr_y + 1 == int_y && !this.isWhite) {
			this.firstMove = false;
			this.isValidMoveBool = true;

		} else {
			this.isValidMoveBool = false;
		}

		// When the move is valid
		if(this.isValidMoveBool) {

			// When checking check is turned on, Check if new move makes king out of check
			if(checkCheck) {

				// create a tmp chessboard with the new move on it in order to check if the new move moves the king out of check or blocks the check
				if(this.kingIsInCheck) {

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
					this.kingIsInCheck = false;
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
}
