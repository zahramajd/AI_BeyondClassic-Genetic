package algorithms.byondClassic;

import common.Node;
import common.PSA;
import common.Problem;

import java.util.ArrayList;

/**
 * Created by zahra on 12/9/2016 AD.
 */
public class RandomRestartHillClimbing {


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

    public ArrayList<Node> my_search(Problem problem) {

        ArrayList<Node> soloutions = new ArrayList<>();

        Node initialNode = new Node();
        created++;

        for (int i = 0; i < 150; i++) {
            soloutions.add(hillclimbing_search(problem, initialNode));

            initialNode = new Node();
            initialNode.state = problem.getRandomNode();
        }

        // FInd best one
        Node best = soloutions.get(0);
        float best_h = problem.heuristic(best.state);

        for (Node n : soloutions) {
            float h = problem.heuristic(n.state);
            if (h < best_h) {
                best_h = h;
                best = n;
            }
        }

        System.out.println("h: "+problem.heuristic(best.state));
        problem.printState(best.state);
        System.out.println("Expanded: " + expanded);
        System.out.println("Created: " + created);
        return solution(best);
    }

    public Node hillclimbing_search(Problem Problem, Node initialNode) {

        Node current = initialNode;
        ArrayList<Node> neighbors = new ArrayList<>();


        while (true) {
            // make neighbors
            neighbors.clear();
            expanded++;

            for (int i = 0; i < Problem.actions(current.state).size(); i++) {
                Node n = PSA.child_node(Problem, current, Problem.actions(current.state).get(i));
                created++;
                neighbors.add(n);
            }

            // choose the best
            boolean no_one_is_better_than_me = true;
            for (Node n : neighbors) {
                if (Problem.heuristic(n.state) < Problem.heuristic(current.state)) {
                    current = n;
                    no_one_is_better_than_me = false;
                }
            }

            if (no_one_is_better_than_me) {
                return current;
            }
        }


    }

}
