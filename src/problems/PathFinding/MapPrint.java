package problems.PathFinding;

import common.Node;

import java.util.ArrayList;

public class MapPrint {

    ArrayList<Coordinate> pathCoordinate = new ArrayList<>();
    Pathfinding pathfinding;
    ArrayList<Node> pathNodes;
    float stepCost=0;

    public MapPrint(Pathfinding pathfinding, ArrayList<Node> pathNodes) {
        this.pathfinding = pathfinding;
        this.pathNodes=pathNodes;

        if (pathNodes != null) {
            for (Node n : pathNodes) {
                stepCost+=n.pathcost;
                for (Coordinate c : pathfinding.states) {
                    if (c.state == n.state) {
                        pathCoordinate.add(0, c);
                    }
                }
            }
        } else {
            System.out.println("No Path found!");
        }

    }

    boolean checkObs(Coordinate from, Coordinate to) {
        for (Obstacle o : pathfinding.obstacles) {
            if ((o.a.getX() == from.getX() && o.a.getY() == from.getY() && o.b.getX() == to.getX() && o.b.getY() == to.getY()) ||
                    (o.a.getX() == to.getX() && o.a.getY() == to.getY() && o.b.getX() == from.getX() && o.b.getY() == from.getY()))
                return true;
        }
        return false;
    }

    void print() {


        Cell[][] map = new Cell[pathfinding.n][pathfinding.m];

        for (int i = 1; i <= pathfinding.n; i++) {
            for (int j = 1; j <= pathfinding.m; j++) {
                boolean da = checkObs(new Coordinate(i, j), new Coordinate(i, j - 1));
                boolean db = checkObs(new Coordinate(i, j), new Coordinate(i + 1, j));
                boolean dc = checkObs(new Coordinate(i, j), new Coordinate(i, j + 1));
                boolean dd = checkObs(new Coordinate(i, j), new Coordinate(i - 1, j));
                map[i - 1][j - 1] = new Cell(null, da, db, dc, dd);
            }
        }

        int s = 0;
        for (Coordinate c : pathCoordinate) {
            map[c.getY() - 1][c.getX() - 1].value = s++;
        }

        for (int i = 0; i < pathfinding.n; i++) {
            for (int j = 0; j < pathfinding.m; j++)
                System.out.print(map[i][j]);

            System.out.print("\r\n");
        }

        System.out.println("-----------------------------");

        System.out.println("Goal Checks:\t"+pathfinding.goalChecks);
        System.out.println("Path Cost :\t\t"+stepCost);
        System.out.println("Path Len:\t\t"+pathNodes.size());

    }

}

class Cell {

    Integer value;
    boolean a, b, c, d;

    Cell(Integer value, boolean a, boolean b, boolean c, boolean d) {
        this.value = value;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }



    @Override
    public String toString() {
        return (value == null ? "." : value) + "  ";
    }
}
