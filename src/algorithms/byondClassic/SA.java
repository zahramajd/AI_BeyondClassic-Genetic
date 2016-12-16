package algorithms.byondClassic;

import common.Node;
import common.PSA;
import common.Problem;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by zahra on 12/9/2016 AD.
 */
public class SA {

    int expanded = 0;
    int created = 0;

    public ArrayList<Node> solution(Node node) {

        ArrayList<Node> path = new ArrayList<>();

        while (node != null) {
            path.add(node);
            node = node.parent;
        }

        return path;
    }

    public ArrayList<Node> SA_search(Problem problem) {

        System.out.println("choose cooling schedule:\n[1] Linear\n[2] Exponential\n[3] Logarithmical");
        Scanner reader = new Scanner(System.in);
        int alg = reader.nextInt();

        Node initialNode = new Node();
        created++;
        initialNode.parent = null;
        initialNode.state = 0;


        Node current = initialNode;
        ArrayList<Node> neighbors = new ArrayList<>();


        // Set initial temp
        double temp = 100000;

        int k = 0;

        while (temp > 1) {
            // make neighbors
            neighbors.clear();
            expanded++;
            for (int i = 0; i < problem.actions(current.state).size(); i++) {
                created++;
                neighbors.add(PSA.child_node(problem, current, problem.actions(current.state).get(i)));

            }

            // choose randomly
            int rand_index = new Random().nextInt(neighbors.size() - 1);
            Node chosen = neighbors.get(rand_index);

            if (checkProbability(problem.heuristic(current.state), problem.heuristic(chosen.state), temp))
                current = chosen;

            k++;
            temp = cooling_schedule(temp, alg, k);
        }


        problem.printState(current.state);
        System.out.println("h: " + problem.heuristic(current.state));
        System.out.println("Expanded: " + expanded);
        System.out.println("Created: " + created);
        return solution(current);

    }

    boolean checkProbability(float currentH, float chosenH, double temp) {


        if (chosenH < currentH)
            return true;

        if (Math.exp((currentH-chosenH) / temp) > Math.random())
            return true;

        return false;
    }

    double cooling_schedule(double temp, int alg, int k) {

        if (alg == 1)
            return linear(temp, k);
        if (alg == 2)
            return exponential(temp, k);
        if (alg == 3)
            return logarithmical(temp, k);

        return 0;
    }

    double linear(double temp, int k) {

        // Cooling rate
        double coolingRate = 0.8;
        return temp - coolingRate * k;
    }

    double exponential(double temp, int k) {

        // Cooling rate
        double coolingRate = 0.8;
        return temp * Math.pow(coolingRate, k);
    }

    double logarithmical(double temp, int k) {

        // Cooling rate
        double coolingRate = 2;
        return temp / (1 + coolingRate * (Math.log(1 + coolingRate)));

    }


}
