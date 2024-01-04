package ants;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
    private int numberOfLarvae = 0;

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

    public synchronized boolean tryToGetLarve() {
        if (this.numberOfLarvae > 0) {
            this.numberOfLarvae--;
            return true;
        }
        return false;
    }

    public synchronized void putLarve() {
        this.numberOfLarvae++;
    }

    public void addRedAnt(Ant ant) {
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
            if (ant.getColor() == Color.RED) {
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
        System.out.println("Getting blue ant: " + ant);
        return ant;
    }

    public void addAnt(Ant ant) {
        if (ant.getColor() == Color.RED) {
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

    public void tryExecuteCommand() {
        // TODO: own threads?
        // try {
        // Command command = commandQueue.take();
        Command command = commandQueue.poll();
        if (command == null) {
            return;
        }
        System.out.println("Vertex is executing command");

        command.execute();
        // } catch (InterruptedException e) {
        // System.err.println("Error while waiting for command");
        // e.printStackTrace();
        // }
    }

    public double distanceTo(Vertex vertex) {
        return Math.sqrt(Math.pow(this.x - vertex.getX(), 2) + Math.pow(this.y - vertex.getY(), 2));
    }
}
