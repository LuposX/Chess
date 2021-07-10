package pieces;

import logic.Board;
import logic.Cell;

public class Knight extends Piece{

	public Knight(Boolean isWhite) {
		super(isWhite, "knight", 3);
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
				kingIsInCheck = true;
			} else {
				this.kingIsInCheck = false;
			}
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
		return checkIfMoveOutOfCheck(curr_x, curr_y, int_x, int_y, board, checkCheck, startPiece, endPiece, kingIsInCheck, isValidMoveBool);
	}
}
