

import java.awt.Point;

/**
 *
 * @author ozkar
 */
public class SnakeBody {

    private Point p;
    private int[] direction = {1, 0};


    public SnakeBody() {
    }

    public SnakeBody(int y, int x) {
        p = new Point(y,x);
    }

    public Point getP() {
        return p;
    }

    public void setP(Point p) {
        this.p = p;
    }

    public int[] getDirection() {
        return direction;
    }

    public void setDirection(int[] direction) {
        this.direction = direction;
    }

    public void setDirection(int direction, int newD) {
        this.direction[direction] = newD;
    }

}
