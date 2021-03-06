package pieces;

import logic.Cell;
import logic.Misc;
import logic.Player;

public class King extends Piece{

	public King(Boolean isWhite) {
		super(isWhite, "king", Integer.MAX_VALUE);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean isValidPath(int curr_x, int curr_y, int int_x, int int_y, Cell[][] board, boolean checkCheck, Piece startPiece, Piece endPiece, Player playerTurn) {

		// Pieces of same color cant capture each other
		if(checkCaptureOwnPiece(curr_x, curr_y, int_x, int_y, board)) {
			return false;
		}

		// Check if King is in check and in checkMate
		if(checkCheck) {
			this.kingIsInCheck = Misc.getCheckCheck(curr_x, curr_y, int_x, int_y, board, startPiece, endPiece, playerTurn);
		}

		if (curr_x + 1 == int_x || curr_x - 1 == int_x) {
			this.isValidMoveBool = true;
			
		} else if (curr_y + 1 == int_y || curr_y - 1 == int_y) {
			this.isValidMoveBool = true;

		} else {
			this.isValidMoveBool = false;
		}
		return checkIfMoveOutOfCheck(curr_x, curr_y, int_x, int_y, board, checkCheck, startPiece, endPiece, kingIsInCheck, isValidMoveBool, playerTurn);
	}

}
