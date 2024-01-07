package ants;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CollectorAnt
 */
public class CollectorAnt extends Ant implements CarryLarvae {
    private AtomicInteger numberOfLarvaes = new AtomicInteger(0);

    public CollectorAnt(String name, int health, int strength, Anthill initialAnthill, Anthill enemyAnthill) {
        super(name, AntColor.RED, health, strength, initialAnthill, enemyAnthill);
    }

    @Override
    protected void doActionsInHomeAnthill() {
        this.setState(AntState.SCANNING);
        this.dropAllLarvae();
        try {
            Thread.sleep(getRandomWaitingTime());
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting in CollectorAnt");
            Thread.currentThread().interrupt();
        }
    }

    @Override
    protected void doActionsInVertex() {
        this.setState(AntState.SCANNING);
        this.tryToHide();

        this.tryToPickLarve();
        if (!this.isAbleToPickLarve()) {
            this.setGoingHome(true);
        }

        this.tryToHide();

        try {
            Thread.sleep(getRandomWaitingTime());
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting in SoldierAnt");
            Thread.currentThread().interrupt();
        }

        if (this.isHidden()) {
            this.unhide();
        }
    }

    @Override
    public AtomicInteger getNumberOfLarvaes() {
        return this.numberOfLarvaes;
    }

    @Override
    public boolean isCarryingLarvae() {
        return this.numberOfLarvaes.get() > 0;
    }

    private boolean isAbleToPickLarve() {
        return this.numberOfLarvaes.get() < this.getStrength();
    }

    @Override
    public void tryToPickLarve() {
        if (!this.isAbleToPickLarve())
            return;

        if (this.currentVertex.tryToGetLarva()) {
            this.addLarve();
        }
    }

    public void addLarve() {
        this.numberOfLarvaes.getAndIncrement();
    }

    @Override
    public void dropLarve() {
        if (this.numberOfLarvaes.get() == 0)
            throw new IllegalStateException("Cannot drop larve when not carrying any");

        this.numberOfLarvaes.decrementAndGet();
        this.currentVertex.putLarva();
    }

    public void dropAllLarvae() {
        while (this.isCarryingLarvae()) {
            this.dropLarve();
        }
    }

    @Override
    protected void beforeDie() {
        if (this.isCarryingLarvae())
            this.dropAllLarvae();
    }

    @Override
    public String info() {
        StringBuffer sb = new StringBuffer( super.info() );
        sb.append("Number of Larvae: " + this.numberOfLarvaes.get() + "\n");
        return sb.toString();
    }
}
