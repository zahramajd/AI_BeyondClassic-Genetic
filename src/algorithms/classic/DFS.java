package algorithms.classic;

import common.Node;
import common.PSA;
import common.Problem;

import java.util.ArrayList;
import java.util.Stack;


public class DFS {


    ArrayList<Integer> explored = new ArrayList<>();

    public ArrayList<Node> solution(Node node) {

        ArrayList<Node> path = new ArrayList<>();

        while (node != null) {
            path.add(node);
            node = node.parent;
        }

        return path;
    }

    public ArrayList<Node> graphDFS(Problem problem) {

        Node initialNode = new Node();
        initialNode.parent = null;
        initialNode.state = 0;

        return recursiveGraphDFS(initialNode, problem);
    }


    private ArrayList<Node> recursiveGraphDFS(Node node, Problem problem) {

        if (problem.goal_test(node.state))
            return solution(node);

        if (explored.contains(node.state))
            return null;
        explored.add(node.state);

        for (int i = 0; i < problem.actions(node.state).size(); i++) {
            Node child = PSA.child_node(problem, node, problem.actions(node.state).get(i));

            ArrayList<Node> path = recursiveGraphDFS(child, problem);

            if (path != null)
                return path;
        }

        return null;
    }


    public ArrayList<Node> treeDFS(Problem problem) {

        Node initialNode = new Node();
        initialNode.parent = null;
        initialNode.state = 0;

        return recursiveTreeDFS(initialNode, problem);
    }


    private ArrayList<Node> recursiveTreeDFS(Node node, Problem problem) {

        if (problem.goal_test(node.state))
            return solution(node);


        for (int i = 0; i < problem.actions(node.state).size(); i++) {
            Node child = PSA.child_node(problem, node, problem.actions(node.state).get(i));

            ArrayList<Node> path = recursiveGraphDFS(child, problem);

            if (path != null)
                return path;
        }

        return null;
    }


}
