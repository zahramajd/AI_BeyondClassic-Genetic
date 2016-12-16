package algorithms.byondClassic;

import common.Node;
import common.PSA;
import common.Problem;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zahra on 12/9/2016 AD.
 */
public class StochasticHillClimbing {

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

    public ArrayList<Node> hillclimbing_search(Problem problem) {


        Node initialNode = new Node();
        created++;
        initialNode.parent = null;
        initialNode.state = 0;


        Node current = initialNode;
        ArrayList<Node> neighbors = new ArrayList<>();

        while (true) {
            // make neighbors
            neighbors.clear();
            expanded++;
            for (int i = 0; i < problem.actions(current.state).size(); i++) {
                created++;
                neighbors.add(PSA.child_node(problem, current, problem.actions(current.state).get(i)));
                System.out.print("\tne: "+problem.heuristic(neighbors.get(i).state));
            }

            // choose the best stochastically
            ArrayList<Node> better_neighbors = new ArrayList<>();

            boolean no_one_is_better_than_me = true;
            for (Node n : neighbors) {
                if (problem.heuristic(n.state) < problem.heuristic(current.state)) {
                    better_neighbors.add(n);
                    no_one_is_better_than_me = false;
                }
            }

            if (no_one_is_better_than_me) {

                System.out.println("\nh: "+problem.heuristic(current.state));
                problem.printState(current.state);
                System.out.println("Expanded: " + expanded);
                System.out.println("Created: " + created);
                return solution(current);
            }

            if (better_neighbors.size() == 1)
                current = better_neighbors.get(0);
            else {
                // make random
                Random rand = new Random();
                int rand_index = rand.nextInt(better_neighbors.size() - 1);
                current = better_neighbors.get(rand_index);
            }

        }

    }
}
