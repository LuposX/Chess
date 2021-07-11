package pieces;

import logic.Cell;

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

		// Check if King is in check and in checkMate
		if(checkCheck) {
			this.kingIsInCheck = super.getCheckCheck(board);

			// Check if in Checkmate
			if(this.kingIsInCheck) {
				if(super.checkCheckMate(board)) {
					System.out.println("In Checkmate");
					return false;
				}
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
		return checkIfMoveOutOfCheck(curr_x, curr_y, int_x, int_y, board, checkCheck, startPiece, endPiece, kingIsInCheck, isValidMoveBool);
	}
}
