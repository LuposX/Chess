package pieces;

import logic.Board;
import logic.Cell;

public class Pawn extends Piece {
	private Boolean firstMove = true;
	private Boolean isWhite;
	
	public Pawn(Boolean isWhite) {
		super(isWhite, "pawn", 1);
		this.isWhite = isWhite;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean isValidPath(int curr_x, int curr_y, int int_x, int int_y, Cell[][] board) {
		// We do -2 because y-axis starts at the top and goes down
		if((curr_x == int_x) && ((curr_y - 2)) == int_y && (firstMove) && isWhite) {
			this.firstMove = false;
			return true;
			
		} else if (curr_x == int_x && (curr_y - 1) == int_y && isWhite) {
			this.firstMove = false;
			return true;
			
		} else if (curr_x == int_x && (curr_y + 1) == int_y && !isWhite) {
			this.firstMove = false;
			return true;
			
		} else if ((curr_x == int_x) && ((curr_y + 2)) == int_y && (firstMove) && !isWhite) {
			this.firstMove = false;
			return true;
			
		} else if ((curr_x + 1 == int_x || curr_x - 1 == int_x) && curr_y - 1 == int_y && isWhite) {
			this.firstMove = false;
			return true;

		} else if ((curr_x + 1 == int_x || curr_x - 1 == int_x) && curr_y + 1 == int_y && !isWhite) {
			this.firstMove = false;
			return true;

		} else {
			return false;
		}
	}
}
