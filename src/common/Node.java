package common;

import java.util.ArrayList;

/**
 * Created by zahra on 11/2/2016 AD.
 */
public class Node {

    public Node parent=null;
    public int state=0;
    public float pathcost=0;
    public boolean expanded = false;
    public ArrayList<Integer> actions;

    public float f=0;

    public float getPathcost() {
        return pathcost;
    }

    public void setPathcost(float pathcost) {
        this.pathcost = pathcost;
    }
}
