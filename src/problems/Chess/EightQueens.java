package problems.Chess;

import algorithms.byondClassic.*;
import algorithms.classic.Astar;
import algorithms.classic.BFS;
import algorithms.classic.DFS;
import algorithms.classic.LDFS;
import common.Node;
import common.Problem;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zahra on 11/2/2016 AD.
 */
public class EightQueens implements Problem {

    ChessState initialNode;
    ArrayList<ChessAction> chessActions;
    ArrayList<ChessState> chessStates;


    public EightQueens() {
        // initial
        this.initialNode = new ChessState();
        chessActions = new ArrayList<>();
        chessStates = new ArrayList<>();

        chessStates.add(initialNode);

        make_actions();
    }

    public void solve(String alg) {

        switch (alg) {
            case "bfs":
                this.solveBFS();
                break;
            case "ldfs":
                this.solveLDFS();
                break;
            case "dfs":
                this.solveDFS();
                break;
            case "a":
                this.solveA();
                break;
            case "hill":
                this.solveHill();
                break;
            case "firsthill":
                this.solvefirstHill();
                break;
            case "randhill":
                this.solveRandHill();
                break;
            case "sthill":
                this.solveStochasticHiil();
                break;
            case "sa":
                this.solveSA();
                break;

        }

    }

    private void solveSA() {
        SA sa = new SA();
        ArrayList<Node> pathNodes = sa.SA_search(this);
        printStats(pathNodes);
    }

    private void solveHill() {
        HillClimbing hc = new HillClimbing();
        ArrayList<Node> pathNodes = hc.hillclimbing_search(this);
        printStats(pathNodes);
    }

    private void solvefirstHill() {
        FirstChoiceHillClimbing fc = new FirstChoiceHillClimbing();
        ArrayList<Node> pathNodes = fc.hillclimbing_search(this);
        printStats(pathNodes);
    }

    private void solveRandHill() {
        RandomRestartHillClimbing rr = new RandomRestartHillClimbing();
        ArrayList<Node> pathNodes = rr.my_search(this);
        printStats(pathNodes);
    }

    private void solveStochasticHiil() {
        StochasticHillClimbing sc = new StochasticHillClimbing();
        ArrayList<Node> pathNodes = sc.hillclimbing_search(this);
        printStats(pathNodes);
    }

    private void solveBFS() {
        BFS bfs = new BFS();
        ArrayList<Node> pathNodes = bfs.treeBFS(this);
        printStats(pathNodes);
    }

    private void solveLDFS() {
        LDFS ldfs = new LDFS();
        ArrayList<Node> pathNodes = ldfs.graphDFS(this, 8);
        printStats(pathNodes);
    }

    private void solveDFS() {
        DFS dfs = new DFS();
        ArrayList<Node> pathNodes = dfs.graphDFS(this);
        printStats(pathNodes);
    }

    private void solveA() {
        Astar a = new Astar();
        ArrayList<Node> pathNodes = a.graphAstar(this);
        printStats(pathNodes);
    }

    private void printStats(ArrayList<Node> pathNodes) {

        System.out.println("-----------------------------------------------");
        System.out.println("Goal Checks:\t\t" + goalChecks);
        System.out.println("Explored Nodes:\t\t" + pathNodes.size());

        float c = 0;
        for (Node n : pathNodes)
            c += n.pathcost;
        System.out.println("Path Cost: " + c);

    }

    public int goalChecks = 0;

    @Override
    public boolean goal_test(int state) {

        goalChecks++;

        ChessState s = null;

        for (ChessState i : this.chessStates)
            if (i.state == state)
                s = i;

        if (s == null)
            return false;

        return s.isGoal();
    }

    @Override
    public ArrayList<Integer> actions(int state) {

        ArrayList<Integer> possibleActions = new ArrayList<>();
        for (int i = 1; i <= 28; i++)
            possibleActions.add(new Integer(i));

        return possibleActions;
    }

    @Override
    public int result(int state, int action) {

        for (ChessState c : chessStates) {
            if (c.state == state) {
                return find_result(c, action);
            }
        }

        return -1;
    }

    @Override
    public float step_cost(int state1, int state2) {
        return 1;
    }

    @Override
    public float heuristic(int state) {

        ChessState cc = null;
        for (ChessState c : chessStates) {
            if (c.state == state) {
                cc = c;
                break;
            }
        }

        if (cc == null)
            return Integer.MAX_VALUE;

        int counter = 0;

        for (int i = 0; i < 7; i++) {
            for (int j = i + 1; j < 8; j++) {
                if (cc.queens[i] == cc.queens[j])
                    counter++;
                else if (Math.abs(cc.queens[i] - cc.queens[j]) == j - i)
                    counter++;
            }
        }


        return counter;
    }

    @Override
    public int getGoalState() {
        return 0;
    }

    @Override
    public int getRandomNode() {
        ChessState newState = new ChessState();
        newState.randomize();
        this.chessStates.add(newState);
        return newState.state;
    }

    @Override
    public void printState(int state) {
        for (ChessState cc : this.chessStates) {
            if (cc.state == state){
                cc.show();
                return;
            }
        }
    }

    public void make_actions() {

        int j = 2;
        for (int i = 0; i < 8; i++) {
            for (j = i; j < 8; j++) {
                if (i != j) {
                    ChessAction ca = new ChessAction(i, j);
                    chessActions.add(ca);
                }
            }
        }

    }

    public int find_result(ChessState c, int action) {


        ChessState chessResult = new ChessState();

        for (int i = 0; i < 8; i++)
            chessResult.queens[i] = c.queens[i];

        ChessAction ca = chessActions.get(action - 1);

        int temp = chessResult.queens[ca.b];
        chessResult.queens[ca.b] = chessResult.queens[ca.a];
        chessResult.queens[ca.a] = temp;

        for (ChessState co : chessStates) {
            if (co.equals(chessResult))
                return co.state;
        }

        this.chessStates.add(chessResult);
        return chessResult.state;
    }
}
