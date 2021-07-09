package pieces;

public class Bishop extends Piece{

	public Bishop(Boolean isWhite) {
		super(isWhite, "bishop", 3);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean isValidPath(int curr_x, int curr_y, int int_x, int int_y, boolean endPieceNull) {
		
		// Calculate difference between start and end position
		int differenceX = (int) Math.sqrt(Math.pow((curr_x - int_x), 2));
		int differenceY = (int) Math.sqrt(Math.pow((curr_y - int_y), 2));
		
		if(differenceX == differenceY) {
			return true;
			
		} else {
			return false;
		}
	}
}
