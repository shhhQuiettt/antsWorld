package ants.anttypes;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import ants.map.Vertex;
import ants.map.Anthill;
import ants.interfaces.AntDeathSubscriber;
import ants.interfaces.AntStateSubscriber;
import ants.commands.Command;

/**
 * Ant
 */
public abstract class Ant {
    protected static int getRandomWaitingTime() {
        return (int) (700 + Math.random() * 1000);
    }
    protected Vertex currentVertex;

    protected Vertex nextVertex = null;
    protected Anthill homeAnthill;

    protected Anthill enemyAnthill;
    // protected int x;
    // protected int y;
    protected double x;

    protected double y;

    protected double velocity;

    protected boolean isGoingHome = false;
    private AntState state = AntState.SCANNING;
    private ArrayList<AntStateSubscriber> subscribers = new ArrayList<>();

    private ArrayList<AntDeathSubscriber> deathSubscribers = new ArrayList<>();

    protected AntColor color;
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int strength;

    protected boolean isHidden = false;
    private final Semaphore actionsSemaphore = new Semaphore(1);

    public final Semaphore leaveVertexSemaphore = new Semaphore(1);

    public Ant(String name, AntColor color, int health, int strength, Anthill initialAnthill, Anthill enemyAnthill) {
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
        this.enemyAnthill = enemyAnthill;
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

    public double getVelocity() {
        return velocity;
    }

    public boolean isHidden() {
        return isHidden;
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

        if (this.state == AntState.DEAD) {
            return;
        }

        if (this.state == AntState.DYING && state != AntState.DEAD) {
            return;
        }

        this.state = state;
        this.notifyStateSubscribers();

        if (state == AntState.DEAD) {
            this.notifyDeathSubscribers();
        }
    }

    /**
     * One iterations of ant's actions, between going to next vertex and 
     * doing actions in vertex
     */
    public final void iterateAction() {
        this.setState(AntState.MOVING);
        this.goToNextVertex();

        this.setState(AntState.SCANNING);

        if (this.isInHomeAnthill()) {
            this.setGoingHome(false);
            this.doActionsInHomeAnthill();
        } else {
            this.doActionsInVertex();
        }

        if (this.currentVertex.isStone()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (!this.isAlive()) {
            this.onDeath();
            return;
        }
    }

    /**
     * Template method for actions in vertex
     */
    protected abstract void doActionsInVertex();


    /**
     * Template method for actions in home anthill
     */
    protected void doActionsInHomeAnthill() {
    }

    /**
     * Template method for actions on death
     */
    protected void onDeath() {
    }

    /**
     * Subscribes to ant's state changes
     * @param subscriber
     */
    public void subscribeStateChange(AntStateSubscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribeState(AntStateSubscriber subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void notifyStateSubscribers() {
        for (AntStateSubscriber subscriber : this.subscribers) {
            subscriber.onAntStateChange(this.state);
        }
    }


    /**
     * Subscribes to ant's death
     * @param subscriber
     */
    public void subscribeDeath(AntDeathSubscriber subscriber) {
        this.deathSubscribers.add(subscriber);
    }

    public void unsubscribeDeath(AntDeathSubscriber subscriber) {
        this.deathSubscribers.remove(subscriber);
    }

    public void notifyDeathSubscribers() {
        for (AntDeathSubscriber subscriber : this.deathSubscribers) {
            subscriber.onAntDeath(this);
        }
    }

    public AntColor getColor() {
        return color;
    }

    public Vertex getCurrentVertex() {
        return currentVertex;
    }


    public boolean isGoingHome() {
        return isGoingHome;
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

    public final void die() {
        if (!this.isAlive()) {
            return;
        }

        this.setState(AntState.DYING);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Ant.this.setState(AntState.DEAD);
                Ant.this.currentVertex.removeAnt(Ant.this);
            }
        }, 1000);

    }

    public String info() {
        StringBuffer sb = new StringBuffer();
        sb.append("Name: " + this.getName() + "\n");
        sb.append("Health: " + this.getHealth() + "\n");
        sb.append("Strength: " + this.getStrength() + "\n");
        sb.append("Speed: " + this.getVelocity() + "\n");
        sb.append("State: " + this.getState() + "\n");
        sb.append("Position X: " + Math.round(this.getX() * 100) / 100.0 + "\n");
        sb.append("Position Y: " + Math.round(this.getY() * 100) / 100.0 + "\n");
        sb.append("Current Vertex: " + this.getCurrentVertex() + "\n");
        sb.append("Next Vertex: " + this.nextVertex + "\n");
        sb.append("Going home: " + this.isGoingHome() + "\n");
        sb.append("Is hidden: " + this.isHidden() + "\n");
        sb.append("Is alive: " + this.isAlive() + "\n");
        return sb.toString();
    }

    protected void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    protected void hide() {
        this.setHidden(true);
    }

    protected void unhide() {
        this.state = AntState.SCANNING;
        this.setHidden(false);
    }

    protected void tryToHide() {
        if (this.currentVertex.isLeaf()) {
            this.setState(AntState.HIDING);
            this.hide();
        }
    }

    protected void setGoingHome(boolean goingHome) {
        isGoingHome = goingHome;
    }

    protected void goToNextVertex() {
        try {
            this.leaveVertexSemaphore.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (!this.isAlive()) {
            return;
        }

        this.nextVertex = this.chooseNextVertex();

        int xNoise = (int) (Math.random() * 60) - 30;
        int yNoise = (int) (Math.random() * 60) - 30;

        Point destination = new Point(this.nextVertex.getX() + xNoise, this.nextVertex.getY() + yNoise);

        double distance = Math
                .sqrt(Math.pow(destination.getX() - this.x, 2) + Math.pow(destination.getY() - this.y, 2));

        this.currentVertex.removeAnt(this);
        this.currentVertex = null;

        double velocityScalar = this.velocity / distance;

        double xVelocity = velocityScalar * (destination.getX() - this.x);
        double yVelocity = velocityScalar * (destination.getY() - this.y);

        while (!isNear(destination)) {
            this.x += xVelocity;
            this.y += yVelocity;
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.currentVertex = this.nextVertex;
        this.currentVertex.addAnt(this);
        this.nextVertex = null;

        this.leaveVertexSemaphore.release();
    }

    protected boolean isInHomeAnthill() {
        return this.currentVertex == this.homeAnthill;
    }

    protected void doScanning() {
        try {
            Thread.sleep(getRandomWaitingTime());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected void sendCommandAndAwaitExecution(Command command) {
        currentVertex.addCommand(command);
        try {
            command.executionSemaphore.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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
        Vertex v = neighbours.get(index);
        while (v == this.enemyAnthill) {
            index = (int) (Math.random() * neighbours.size());
            v = neighbours.get(index);
        }
        return v;
    }

    private Vertex getToHomeVertex() {
        Vertex nearestHomeVertex = this.currentVertex.getNeighbors().get(0);

        for (Vertex neigbour : this.currentVertex.getNeighbors()) {
            if (nearestHomeVertex.distanceTo(this.homeAnthill) > neigbour.distanceTo(this.homeAnthill)) {
                nearestHomeVertex = neigbour;
            }
        }

        if (nearestHomeVertex == this.enemyAnthill) {
            return this.getRandomVertex();
        }

        return nearestHomeVertex;
    }

    private boolean isNear(Point targetPoint) {
        return (Math.abs(this.x - targetPoint.x) <= 2 * this.velocity
                && Math.abs(this.y - targetPoint.y) <= 2 * this.velocity);
    }

}
