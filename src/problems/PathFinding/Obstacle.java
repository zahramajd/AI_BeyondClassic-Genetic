package problems.PathFinding;

/**
 * Created by zahra on 11/10/2016 AD.
 */
public class Obstacle {

    public Coordinate a;
    public Coordinate b;

    public Obstacle(int x1, int y1, int x2, int y2) {
        System.out.println("Obstacle: " + x1 + "," + y1 + "  " + x2 + "," + y2);

        a = new Coordinate(x1, y1);
        a.state = -1;

        b = new Coordinate(x2, y2);
        b.state = -1;
    }

}
