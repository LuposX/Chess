package logic;

import pieces.Piece;

public class Cell {
	private Piece piece;
	private int Y;
	private int X;
	
	public Cell(int y, int x, Piece piece) {
		this.piece = piece;
		this.X = x;
		this.Y = y;
	}
	
	public Piece getPiece() {
		return this.piece;
	}
	
	public void setPiece(Piece pi) {
		this.piece = pi;
	}
	
	public int getX() {
		return this.X;
	}
	
	public int getY() {
		return this.Y;
	}
	
	public void setX(int x) {
		this.X = x;
	}
	
	public void setY(int y) {
		this.Y = y;
	}
}
