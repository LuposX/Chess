package pieces;

import logic.Board;
import logic.Cell;

public class Bishop extends Piece{

	public Bishop(Boolean isWhite) {
		super(isWhite, "bishop", 3);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean isValidPath(int curr_x, int curr_y, int int_x, int int_y, Cell[][] chessBoard) {
		
		// Calculate difference between start and end position
		int differenceX = Math.abs(curr_x - int_x);
		int differenceY = Math.abs(curr_y - int_y);
		
		if(differenceX == differenceY) {

			// Direction from source: bottom right
			if(curr_x + differenceX == int_x && curr_y + differenceY == int_y) {

				// Check if a piece is blocking the path
				for (int i = 1; i < differenceX; i++) {
					if (chessBoard[curr_y + i][curr_x + i].getPiece() != null) {
						return false;
					}
				}
				return true;

			// Direction from source: bottom left
			} else if (curr_x - differenceX == int_x && curr_y + differenceY == int_y) {

				// Check if a piece is blocking the path
				for (int i = 1; i < differenceX; i++) {
					if (chessBoard[curr_y + i][curr_x - i].getPiece() != null) {
						return false;
					}
				}
				return true;

			// Direction from source: top right
			} else if (curr_x + differenceX == int_x && curr_y - differenceY == int_y) {

				// Check if a piece is blocking the path
				for (int i = 1; i < differenceX; i++) {
					if (chessBoard[curr_y - i][curr_x + i].getPiece() != null) {
						return false;
					}
				}
				return true;

			// Direction from source: top left
			} else {

				// Check if a piece is blocking the path
				for (int i = 1; i < differenceX; i++) {
					if (chessBoard[curr_y - i][curr_x - i].getPiece() != null) {
						return false;
					}
				}
				return true;

			}
		} else {
			return false;
		}
	}
}
