package logic;

import java.awt.Point;

import pieces.Piece;

public class Move {
	private Boolean isWhite;
	private Point startPoint;
	private Point endPoint;
	private Piece piece;
	private Boolean isCastlingMove = false;
	
	/**
	 * Creates a Move Object
	 * @param isWhite which player did the move
	 * @param startPoint where did the move start
	 * @param endPoint where did the move end
	 * @param piece which piece was sued for this move
	 */
	public Move(Boolean isWhite, Point startPoint, Point endPoint, Piece piece) {
		this.setIsWhite(isWhite);
		this.setStartPoint(startPoint);
		this.setEndPoint(endPoint);
		this.setPiece(piece);
	}
	
	public Move(Boolean isWhite, Point startPoint, Point endPoint, Piece piece, Boolean isCastlingMove) {
		this.setIsWhite(isWhite);
		this.setStartPoint(startPoint);
		this.setEndPoint(endPoint);
		this.setPiece(piece);
		this.setIsCastlingMove(isCastlingMove);
	}

	public Boolean getIsWhite() {
		return isWhite;
	}

	public void setIsWhite(Boolean isWhite) {
		this.isWhite = isWhite;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	public Boolean getIsCastlingMove() {
		return isCastlingMove;
	}

	public void setIsCastlingMove(Boolean isCastlingMove) {
		this.isCastlingMove = isCastlingMove;
	}
	
}
