package ants;

import java.util.ArrayList;

enum AntState {
    SCANNING, MOVING, ATTACKING, DYING
}

/**
 * Ant
 */
public abstract class Ant {
    private Vertex currentVertex;
    private Vertex nextVertex = null;

    private boolean isGoingHome = false;
    private AntState state = AntState.SCANNING;

    public Ant(Vertex currentVertex) {
        this.currentVertex = currentVertex;
    }

    public boolean isGoingHome() {
        return isGoingHome;
    }

    public void setGoingHome(boolean goingHome) {
        isGoingHome = goingHome;
    }

    public AntState getState() {
        return state;
    }

    public void setState(AntState state) {
        this.state = state;
    }

    public Vertex getCurrentVertex() {
        return currentVertex;
    }

    //// CONCURENCY?
    protected void setNextVertex() {
        if (this.isGoingHome) {
            this.nextVertex =  this.getToHomeVertex();
        } else {
            this.nextVertex =  this.getRandomVertex();
        }
    }

    //// CONCURENCY?
    protected Vertex getRandomVertex() {
        ArrayList<Vertex> neighbours = currentVertex.getNeighbors();
        int index = (int) (Math.random() * neighbours.size());
        return neighbours.get(index);
    }

    protected Vertex getToHomeVertex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public abstract void doAction();
}
