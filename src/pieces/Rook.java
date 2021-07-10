package pieces;

import logic.Cell;

public class Rook extends Piece{

	public Rook(Boolean isWhite) {
		// Calls Parent constructor
		super(isWhite, "rook", 5);		
	}

	@Override
	public Boolean isValidPath(int curr_x, int curr_y, int int_x, int int_y, Cell[][] chessBoard) {

		// Calculate difference between start and end position
		int differenceX = (int) Math.sqrt(Math.pow((curr_x - int_x), 2));
		int differenceY = (int) Math.sqrt(Math.pow((curr_y - int_y), 2));

		if (curr_x == int_x) {

			if(curr_y > int_y) {
				// Check that no piece is between the path
				for (int i = 1; i < differenceY; i++) {
					if (chessBoard[curr_y - i][curr_x].getPiece() != null) {
						return false;
					}
				}
				return true;  // if no piece is in the way
			} else {
				// Check that no piece is between the path
				for (int i = 1; i < differenceY; i++) {
					if (chessBoard[curr_y + i][curr_x].getPiece() != null) {
						return false;
					}
				}
				return true;  // if no piece is in the way
			}

		} else if (curr_y == int_y) {

			if(curr_x > int_x) {
				// Check that no piece is between the path
				for (int i = 1; i < differenceX; i++) {
					if (chessBoard[curr_y][curr_x - i].getPiece() != null) {
						return false;
					}
				}
				return true;  // if no piece is in the way
			} else {
				// Check that no piece is between the path
				for (int i = 1; i < differenceX; i++) {
					if (chessBoard[curr_y][curr_x + i].getPiece() != null) {
						return false;
					}
				}
				return true;  // if no piece is in the way
			}
		} else {
			return false;
		}
	}
}
