package pieces;

public class King extends Piece{

	public King(Boolean isWhite) {
		super(isWhite, "king", Integer.MAX_VALUE);
		// TODO Auto-generated constructor stub
	}


	@Override
	public Boolean isValidPath(int curr_x, int curr_y, int int_x, int int_y, boolean endPieceNull) {
		if (curr_x + 1 == int_x || curr_x - 1 == int_x) {
			return true;
			
		} else if (curr_y + 1 == int_y || curr_y - 1 == int_y) {
			return true;

		} else {
			return false;
		}
	}


}
