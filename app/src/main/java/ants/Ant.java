package ants;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;
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

    // protected int x;
    // protected int y;
    protected double x;
    protected double y;

    protected double velocity;

    protected boolean isGoingHome = false;

    private AntState state = AntState.SCANNING;
    private ArrayList<AntStateSubscriber> subscribers = new ArrayList<>();
    private ArrayList<AntDeathSubscriber> deathSubscribers = new ArrayList<>();

    protected Color color;

    protected String name;
    protected int health;
    protected int maxHealth;
    protected int strength;

    // private final Lock mutex = new ReentrantLock();
    private final Semaphore actionsSemaphore = new Semaphore(1);
    public final Semaphore leaveVertexSemaphore = new Semaphore(1);

    public Ant(String name, Color color, int health, int strength, Anthill initialAnthill) {
        this.name = name;
        this.currentVertex = initialAnthill;
        this.velocity = 1.0;
        this.color = color;
        this.maxHealth = health;
        this.health = health;
        this.strength = strength;

        this.x = initialAnthill.getX();
        this.y = initialAnthill.getY();
        this.homeAnthill = initialAnthill;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
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

    public synchronized AntState getState() {
        return state;
    }

    public synchronized void setState(AntState state) {
        if (state == this.state) {
            return;
        }

        this.state = state;
        this.notifyStateSubscribers();

        if (state == AntState.DEAD) {
            this.notifyDeathSubscribers();
        }
    }

    public void subscribeStateChange(AntStateSubscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribeState(AntStateSubscriber subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void subscribeDeath(AntDeathSubscriber subscriber) {
        this.deathSubscribers.add(subscriber);
    }

    public void unsubscribeDeath(AntDeathSubscriber subscriber) {
        this.deathSubscribers.remove(subscriber);
    }

    public void notifyStateSubscribers() {
        for (AntStateSubscriber subscriber : this.subscribers) {
            subscriber.onAntStateChange(this.state);
        }
    }

    public void notifyDeathSubscribers() {
        for (AntDeathSubscriber subscriber : this.deathSubscribers) {
            subscriber.onAntDeath(this);
        }
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

    public boolean isAlive() {
        return this.state != AntState.DEAD && this.state != AntState.DYING;
    }

    public void acquireActionsSemaphore() throws InterruptedException {
        actionsSemaphore.acquire();
    }

    public void releaseActionsSemaphore() {
        actionsSemaphore.release();
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
        // return neighbours.get(index);
        // TODO: fix this
        Vertex v = neighbours.get(index);
        if (this.currentVertex instanceof Anthill && v instanceof Anthill) {
            return getRandomVertex();
        } else {
            return v;
        }
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

    protected void beforeDie() {

    }

    protected final void die() {
        this.beforeDie();

        System.out.println(this.color + " Ant " + this.name + " is dying");
        this.setState(AntState.DYING);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("While waiting for ant to die, Ant was interrupted");
            Thread.currentThread().interrupt();
        }

        this.setState(AntState.DEAD);
        this.currentVertex.removeAnt(this);
    }

    protected void goToNextVertex() {
        try {
            this.leaveVertexSemaphore.acquire();
        } catch (InterruptedException e) {
            System.err.println("While waiting for leaveVertexSemaphore to be released, Ant was interrupted");
            Thread.currentThread().interrupt();
        }

        if (!this.isAlive()) {
            return;
        }

        this.nextVertex = this.chooseNextVertex();

        double distance = Math.sqrt(Math.pow(this.nextVertex.getX() - this.x, 2)
                + Math.pow(this.nextVertex.getY() - this.y, 2));

        this.currentVertex.removeAnt(this);
        this.currentVertex = null;

        double velocityScalar = this.velocity / distance;

        double xVelocity = velocityScalar * (this.nextVertex.getX() - this.x);
        double yVelocity = velocityScalar * (this.nextVertex.getY() - this.y);

        // at least one pixel per frame but have to keep scale
        // if (Math.abs(xVelocity) < 1) {
        // xVelocity = Math.signum(xVelocity);
        // }
        // if (Math.abs(yVelocity) < 1) {
        // yVelocity = Math.signum(yVelocity);
        // }

        while (!isNearCoords(this.nextVertex.getX(), this.nextVertex.getY())) {
            this.x += xVelocity;
            this.y += yVelocity;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.err.println("While waiting for ant to move, Ant was interrupted");
                // Exit thread
                Thread.currentThread().interrupt();
            }
        }
        this.currentVertex = this.nextVertex;
        this.currentVertex.addAnt(this);
        this.nextVertex = null;

        this.leaveVertexSemaphore.release();
    }

    private boolean isNearCoords(int targetX, int targetY) {
        return (Math.abs(this.x - targetX) <= 5 * this.velocity && Math.abs(this.y - targetY) <= 5 * this.velocity);
    }

    public void lockAndDoActions() {
        // try {
        // this.acquireActionsSemaphore();
        // } catch (InterruptedException e) {
        // System.err.println("While waiting for actions semaphore to be released, Ant
        // was interrupted");
        // }
        // if (!this.isAlive()) {
        // return;
        // }

        doActionsInVertex();

        if (!this.isAlive()) {
            return;
        }

        this.setState(AntState.MOVING);
        this.goToNextVertex();
        this.setState(AntState.SCANNING);
    }

    protected void sendCommandAndWait(Command command) {
        currentVertex.addCommand(command);
        try {
            command.executionSemaphore.acquire();
        } catch (InterruptedException e) {
            System.err.println("While waiting for command to be released, Ant was interrupted");
            Thread.currentThread().interrupt();
        }

        // try {
        // Thread.sleep(1000);
        // } catch (InterruptedException e) {
        // System.err.println("While waiting for command to be executed, Ant was
        // interrupted");
        // }
    }

    protected abstract void doActionsInVertex();
}
