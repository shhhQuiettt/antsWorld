package ants;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

enum AntState {
    SCANNING, MOVING, ATTACKING, DYING, DEAD
}

/**
 * Ant
 */
public abstract class Ant {
    protected Vertex currentVertex;
    protected Vertex nextVertex = null;

    protected Anthill homeAnthill;

    protected int x;
    protected int y;
    protected double velocity;

    protected boolean isGoingHome = false;
    protected AntState state = AntState.SCANNING;
    protected Color color;

    protected String name;
    protected int health;
    protected int maxHealth;
    protected int strength;

    public final Lock mutex = new ReentrantLock();

    public Ant(String name, Color color, int health, int strength, Anthill initialAnthill) {
        this.name = name;
        this.currentVertex = initialAnthill;
        this.velocity = 3.0;
        this.color = color;
        this.maxHealth = health;
        this.health = health;
        this.strength = strength;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int decreaseHealth(int amount) {
        this.health = Math.max(this.health - amount, 0);

        if (this.health == 0) {
            this.die();
        }

        return this.health;
    }

    public void recoverHealth() {
        this.health = this.maxHealth;
    }

    public int getStrength() {
        return strength;
    }

    public AntState getState() {
        return state;
    }

    public void setState(AntState state) {
        this.state = state;
    }

    public Color getColor() {
        return color;
    }

    public Vertex getCurrentVertex() {
        return currentVertex;
    }

    public boolean isGoingHome() {
        return isGoingHome;
    }

    public void setGoingHome(boolean goingHome) {
        isGoingHome = goingHome;
    }

    private Vertex chooseNextVertex() {
        if (this.isGoingHome) {
            this.nextVertex = this.getToHomeVertex();
        } else {
            this.nextVertex = this.getRandomVertex();
        }
        return this.nextVertex;
    }

    private Vertex getRandomVertex() {
        ArrayList<Vertex> neighbours = currentVertex.getNeighbors();
        int index = (int) (Math.random() * neighbours.size());
        return neighbours.get(index);
    }

    private Vertex getToHomeVertex() {
        Vertex nearestHomeVertex = this.currentVertex.getNeighbors().get(0);

        for (Vertex neigbour : this.currentVertex.getNeighbors()) {
            if (nearestHomeVertex.distanceTo(this.homeAnthill) > neigbour.distanceTo(this.homeAnthill)) {
                nearestHomeVertex = neigbour;
            }
        }

        return nearestHomeVertex;
    }

    protected void die() {
        this.state = AntState.DYING;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("While waiting for ant to die, Ant was interrupted");
        }

        this.state = AntState.DEAD;
        this.currentVertex.removeAnt(this);
    }

    protected void goToNextVertex() {
        this.nextVertex = this.chooseNextVertex();
        this.currentVertex.removeAnt(this);

        double distance = Math.sqrt(Math.pow(this.nextVertex.getX() - this.x, 2)
                + Math.pow(this.nextVertex.getY() - this.y, 2));

        this.currentVertex = null;

        double velocityScalar = this.velocity / distance;

        double xVelocity = velocityScalar * (this.nextVertex.getX() - this.x);
        double yVelocity = velocityScalar * (this.nextVertex.getY() - this.y);

        while (!isNearCoords(this.nextVertex.getX(), this.nextVertex.getY())) {
            this.x += xVelocity;
            this.y += yVelocity;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.err.println("While waiting for ant to move, Ant was interrupted");
            }
        }
        this.currentVertex = this.nextVertex;
        this.currentVertex.addAnt(this);

        this.nextVertex = null;
    }

    private boolean isNearCoords(int targetX, int targetY) {
        return (Math.abs(this.x - targetX) <= this.velocity && Math.abs(this.y - targetY) <= this.velocity);
    }

    public void lockAndDoActions() {
        mutex.lock();
        doActions();
        mutex.unlock();
    }

    protected void sendCommand(Command command) {
        currentVertex.addCommand(command);
        try {
            command.semaphore.acquire();
        } catch (InterruptedException e) {
            System.err.println("While waiting for command to be released, Ant was interrupted");
        }
    }

    protected abstract void doActions();
}
