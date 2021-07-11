package pieces;

import logic.Cell;
import logic.Misc;
import logic.Player;

public class Knight extends Piece{

	public Knight(Boolean isWhite) {
		super(isWhite, "knight", 3);
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

		// Calculate difference between start and end position
		int differenceX = (int) Math.sqrt(Math.pow((curr_x - int_x), 2));
		int differenceY = (int) Math.sqrt(Math.pow((curr_y - int_y), 2));
		
		if(differenceX == 1 && differenceY == 2) {
			this.isValidMoveBool = true;
			
		} else if (differenceX == 2 && differenceY == 1) {
			this.isValidMoveBool = true;
			
		} else {
			this.isValidMoveBool = false;
		}
		return checkIfMoveOutOfCheck(curr_x, curr_y, int_x, int_y, board, checkCheck, startPiece, endPiece, kingIsInCheck, isValidMoveBool, playerTurn);
	}
}
