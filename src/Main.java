import genetic.GA;
import genetic.SolveEquation;
import problems.Chess.EightQueens;
import problems.PathFinding.Obstacle;
import problems.PathFinding.Pathfinding;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
//        EightQueens eq = new EightQueens();
//        eq.solve("sa");

        SolveEquation se=new SolveEquation();
        GA g=new GA(se,200);

        g.algorithm();

    }

    private static void maze() throws Exception {
        // Scanner input = new Scanner(System.in);
        Scanner input = new Scanner(new FileInputStream("input.txt"));

        int m = input.nextInt();
        int n = input.nextInt();

        ArrayList<Obstacle> obstacles = new ArrayList<>();
        for (int i = input.nextInt(); i > 0; i--) {
            obstacles.add(new Obstacle(input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt()));
        }

        Pathfinding pf = new Pathfinding(m, n, obstacles);

        pf.solve("dfs");

    }
}
