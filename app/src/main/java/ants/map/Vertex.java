package ants.map;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import ants.anttypes.Ant;
import ants.anttypes.AntColor;
import ants.commands.Command;
import ants.interfaces.LarvaeSubscriber;

/**
 * Vertex
 */
public class Vertex {
    private final int x;
    private final int y;

    private BlockingQueue<Ant> redAnts = new LinkedBlockingQueue<Ant>();
    private BlockingQueue<Ant> blueAnts = new LinkedBlockingQueue<Ant>();

    private boolean isLeaf;
    private boolean isStone;

    private AtomicInteger numberOfLarvae = new AtomicInteger(0);
    private ArrayList<LarvaeSubscriber> larvaeSubscribers = new ArrayList<>();

    private ArrayList<Vertex> neighbors = new ArrayList<>();

    private BlockingQueue<Command> commandQueue = new LinkedBlockingQueue<Command>();

    public Vertex(int x, int y, boolean isLeaf, boolean isStone) {
        this.x = x;
        this.y = y;
        this.isLeaf = isLeaf;
        this.isStone = isStone;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void addNeighbor(Vertex neighbor) {
        neighbors.add(neighbor);
    }

    public ArrayList<Vertex> getNeighbors() {
        return neighbors;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public boolean isStone() {
        return isStone;
    }

    public synchronized boolean tryToGetLarva() {
        if (this.numberOfLarvae.get() > 0) {
            this.removeLarva();
            return true;
        }
        return false;
    }

    public synchronized void putLarva() {
        this.numberOfLarvae.incrementAndGet();

        for (LarvaeSubscriber subscriber : this.larvaeSubscribers) {
            subscriber.onLarvaeAdded();
        }
    }

    private synchronized void removeLarva() {
        this.numberOfLarvae.decrementAndGet();

        for (LarvaeSubscriber subscriber : this.larvaeSubscribers) {
            subscriber.onLarvaeRemoved();
        }
    }

    public AtomicInteger getNumberOfLarvae() {
        return this.numberOfLarvae;
    }

    public void subscribeLarvae(LarvaeSubscriber subscriber) {
        this.larvaeSubscribers.add(subscriber);
    }

    public void unsubscribeLarvae(LarvaeSubscriber subscriber) {
        this.larvaeSubscribers.remove(subscriber);
    }

    public BlockingQueue<Ant> getRedAnts() {
        return redAnts;
    }

    public BlockingQueue<Ant> getBlueAnts() {
        return blueAnts;
    }

    public void addRedAnt(Ant ant) {
        // System.out.println("adding red ant");
        try {
            this.redAnts.add(ant);
        } catch (IllegalStateException e) {
            System.err.println("No space in redAnts queue");
        }
    }

    public void addBlueAnt(Ant ant) {
        try {
            this.blueAnts.add(ant);
        } catch (IllegalStateException e) {
            System.err.println("No space in redAnts queue");
        }
    }

    public void removeAnt(Ant ant) {
        try {
            if (ant.getColor() == AntColor.RED) {
                redAnts.remove(ant);
            } else {
                blueAnts.remove(ant);
            }
        } catch (NullPointerException e) {
            System.err.println("Ant was not in vertex");
        }
    }

    public boolean redAntInVertex() {
        return redAnts.size() > 0;
    }

    public boolean blueAntInVertex() {
        return blueAnts.size() > 0;
    }

    public Ant getRedAnt() {
        return redAnts.peek();
    }

    public Ant getBlueAnt() {
        Ant ant = blueAnts.peek();
        return ant;
    }

    public void addAnt(Ant ant) {
        if (ant.getColor() == AntColor.RED) {
            this.addRedAnt(ant);
        } else {
            this.addBlueAnt(ant);
        }
    }

    public void addCommand(Command command) {
        try {
            commandQueue.add(command);
        } catch (IllegalStateException e) {
            System.err.println("No space in command queue");
            e.printStackTrace();
        }
    }

    public BlockingQueue<Command> getCommandQueue() {
        return commandQueue;
    }

    // public Command pollCommand() {
    // return commandQueue.poll();
    // }

    // public void tryExecuteCommand() {
    // // TODO: own threads?
    // // try {
    // // Command command = commandQueue.take();
    // Command command = commandQueue.poll();
    // if (command == null) {
    // return;
    // }
    // System.out.println("Vertex is executing command");

    // command.execute();
    // // }void catch (InterruptedException e) {
    // // System.err.println("Error while waiting for command");
    // // e.printStackTrace();
    // // }
    // }

    public double distanceTo(Vertex vertex) {
        return Math.sqrt(Math.pow(this.x - vertex.getX(), 2) + Math.pow(this.y - vertex.getY(), 2));
    }

    public String info() {
        StringBuffer sb = new StringBuffer();
        sb.append("Vertex at: " + this.x + " " + this.y + "\n");
        sb.append("isLeaf: " + this.isLeaf + "\n");
        sb.append("isStone: " + this.isStone + "\n");
        sb.append("Number of red ants: " + this.redAnts.size() + "\n");
        sb.append("Number of blue ants: " + this.blueAnts.size() + "\n");
        sb.append("Number of larvae: " + this.numberOfLarvae.get() + "\n");
        sb.append("Neighbors: \n");
        for (Vertex neighbor : this.neighbors) {
            sb.append("\tat: " + neighbor.getX() + " " + neighbor.getY() + "\n");
        }

        return sb.toString();

    }
}
