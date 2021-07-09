package pieces;

import logic.Board;
import logic.Cell;

public class Knight extends Piece{

	public Knight(Boolean isWhite) {
		super(isWhite, "knight", 3);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean isValidPath(int curr_x, int curr_y, int int_x, int int_y, Cell[][] board) {
		
		// Calculate difference between start and end position
		int differenceX = (int) Math.sqrt(Math.pow((curr_x - int_x), 2));
		int differenceY = (int) Math.sqrt(Math.pow((curr_y - int_y), 2));
		
		if(differenceX == 1 && differenceY == 2) {
			return true;
			
		} else if (differenceX == 2 && differenceY == 1) {
			return true;
			
		} else {
			return false;
		}
	}
}
