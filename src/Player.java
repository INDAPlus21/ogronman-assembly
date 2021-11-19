

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author ozkar
 */
public class Player {

    private ArrayList<SnakeBody> body;

    public Player() {
        body = new ArrayList<>();
        body.add(new SnakeBody(200,200));
    }

    public int getSize() {
        return body.size();
    }

    public void addSize() {
        body.add(new SnakeBody(body.get(body.size()-1).getP().x, body.get(body.size()-1).getP().y));
    }

    public Point getP(int i) {
        return body.get(i).getP();
    }

    public void setP(int i, Point p) {
        body.get(i).setP(p);
    }

    public void updateBody() {
        /**for(int i = 0; i < body.size(); i++){
            body.get(i).getP().setLocation(body.get(i).getP().x+10*body.get(i).getDirection()[0], body.get(i).getP().y+10*body.get(i).getDirection()[1]);
        }
        for(int i = 1; i < body.size(); i++){
            body.get(i).setDirection(body.get(i-1).getDirection());
        }*/
        for(int i = 1; i < body.size(); i++){
            body.get(i).setP(body.get(i-1).getP());
        }
        body.get(0).setP(new Point(body.get(0).getP().x+10*body.get(0).getDirection()[0], body.get(0).getP().y+10*body.get(0).getDirection()[1]));
    }

    public Point getBody(int i) {
        return body.get(i).getP();
    }

    public void setDirection(int direction, int newD) {
        body.get(0).setDirection(direction, newD);
    }


}
