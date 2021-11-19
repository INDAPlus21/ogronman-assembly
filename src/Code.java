import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author ozkar
 */
public class Code {

    private Point p;
    private int type;

    public Code(int t) {
        Random r = new Random();
        p = new Point(r.nextInt(1000), r.nextInt(1000));
        if (t == 0) {
            type = r.nextInt(8);
        } else {
            type = r.nextInt(8) + 8;

        }
    }

    public Point getP() {
        return p;
    }

    public void setP(Point p) {
        this.p = p;
    }

    public int getType() {
        return type;
    }

}
