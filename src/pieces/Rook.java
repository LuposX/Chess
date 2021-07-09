package pieces;

public class Rook extends Piece{

	public Rook(Boolean isWhite) {
		// Calls Parent constructor
		super(isWhite, "rook", 5);		
	}

	@Override
	public Boolean isValidPath(int curr_x, int curr_y, int int_x, int int_y, boolean endPieceNull) {
		if (curr_x == int_x) {
			return true;
			
		} else if (curr_y == int_y) {
			return true;
			
		} else {
			return false;
		}
	}

}
