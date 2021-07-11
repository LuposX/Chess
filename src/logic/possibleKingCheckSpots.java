package logic;

import java.awt.Point;

public class possibleKingCheckSpots {
    private Point point;
    private boolean isPointCovered; // this is true when a piece is looking on it

    public possibleKingCheckSpots(Point point) {
        this.point = point;
        this.isPointCovered = false;
    }

    public Point getPoint() {
        return this.point;
    }

    public boolean getIsPointCovered() {
        return this.isPointCovered;
    }

    public void setPointCovered(boolean pointCovered) {
        this.isPointCovered = pointCovered;
    }
}
