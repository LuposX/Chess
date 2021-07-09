package logic;

public class Player {
	private boolean isWhiteSide;
	private int Points;
	
	public Player(Boolean isWhiteSide) {
		this.isWhiteSide = isWhiteSide;
	}
	
	public boolean getIsWhiteSide() {
		return this.isWhiteSide;
	}

	public int getPoints() {
		return Points;
	}

	public void setPoints(int points) {
		Points = points;
	}
}
