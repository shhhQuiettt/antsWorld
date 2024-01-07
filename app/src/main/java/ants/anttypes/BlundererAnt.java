package ants.anttypes;

import java.util.concurrent.atomic.AtomicInteger;

import ants.interfaces.CarryLarvae;
import ants.interfaces.LarvaeSubscriber;
import ants.map.Anthill;

import java.util.ArrayList;

/**
 * BlundererAnt
 * Ant with a probability of dropping a larva when is going home.
 */
public class BlundererAnt extends Ant implements CarryLarvae {
    AtomicInteger numberOfLarvaes = new AtomicInteger(0);
    double probabilityOfDroppingLarva;

    private ArrayList<LarvaeSubscriber> larvaeSubscribers = new ArrayList<>();

    public BlundererAnt(String name, int health, int strength, double probabilityOfDroppingLarva,
            Anthill initialAnthill, Anthill enemyAnthill) {
        super(name, AntColor.RED, health, strength, initialAnthill, enemyAnthill);
        this.probabilityOfDroppingLarva = probabilityOfDroppingLarva;
    }

    @Override
    public void doActionsInVertex() {
        this.tryToHide();

        this.doScanning();

        this.tryToPickLarve();

        if (!this.isAbleToPickLarve()) {
            this.setGoingHome(true);
        }

        if (this.isGoingHome() && this.isCarryingLarvae()) {
            if (this.hasDroppedLarve()) {
                this.dropLarve();
            }
        }

        if (this.isHidden())
            this.unhide();
    }

    @Override
    protected void doActionsInHomeAnthill() {
        this.dropAllLarvae();
    }

    private boolean hasDroppedLarve() {
        return Math.random() < this.probabilityOfDroppingLarva;
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
            this.notifyLarvaeSubscribersForAdd();
        }
    }

    public void addLarve() {
        this.numberOfLarvaes.getAndIncrement();
    }

    @Override
    public void dropLarve() {
        if (this.numberOfLarvaes.get() == 0)
            return;

        this.numberOfLarvaes.decrementAndGet();
        this.currentVertex.putLarva();
        this.notifyLarvaeSubscribersForDrop();
    }

    public void dropAllLarvae() {
        while (this.isCarryingLarvae()) {
            this.dropLarve();
        }
    }


    /**
     * Subscribe to larvae events
     * @param subscriber
     */
    public void subscribeLarvae(LarvaeSubscriber subscriber) {
        this.larvaeSubscribers.add(subscriber);
    }

    public void unsubscribeLarvae(LarvaeSubscriber subscriber) {
        this.larvaeSubscribers.remove(subscriber);
    }

    private void notifyLarvaeSubscribersForAdd() {
        for (LarvaeSubscriber subscriber : this.larvaeSubscribers) {
            subscriber.onLarvaeAdded();
        }
    }

    private void notifyLarvaeSubscribersForDrop() {
        for (LarvaeSubscriber subscriber : this.larvaeSubscribers) {
            subscriber.onLarvaeRemoved();
        }
    }

    @Override
    protected void onDeath() {
        if (this.isCarryingLarvae())
            this.dropAllLarvae();
    }

    @Override
    public String info() {
        StringBuffer sb = new StringBuffer( super.info() );
        sb.append("Number of Larvae: " + this.numberOfLarvaes.get() + "\n");
        sb.append("Probability of dropping a larva: " + this.probabilityOfDroppingLarva + "\n");
        return sb.toString();
    }
}
