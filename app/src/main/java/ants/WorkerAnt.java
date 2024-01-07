package ants;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;

/**
 * CollectorAnt
 */
public class WorkerAnt extends Ant implements Attacking, CarryLarvae, LarvaeInfoEmiter {
    private AtomicInteger numberOfLarvaes = new AtomicInteger(0);

    private ArrayList<LarvaeSubscriber> larvaeSubscribers = new ArrayList<>();

    public WorkerAnt(String name, int health, int strength, Anthill initialAnthill, Anthill enemyAnthill) {
        super(name, AntColor.BLUE, health, strength, initialAnthill, enemyAnthill);
    }

    @Override
    protected void doActionsInHomeAnthill() {
        this.setState(AntState.SCANNING);
        this.dropAllLarvae();
        try {
            Thread.sleep(getRandomWaitingTime());
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting in WorkerAnt");
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

        this.tryToAttack();

        try {
            Thread.sleep(getRandomWaitingTime());
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting in WorkerAnt");
        }

        this.tryToAttack();

        if (this.isHidden()) {
            this.unhide();
        }
    }

    ////////////
    //

    public Ant getEnemy() {
        if (currentVertex.redAntInVertex()) {
            return currentVertex.getRedAnt();
        }
        return null;
    }

    private void tryToAttack() {
        Ant potentialVictim = this.getEnemy();
        if (potentialVictim != null) {
            this.setState(AntState.ATTACKING);
            this.attack(potentialVictim);
            this.setState(AntState.SCANNING);
            this.setGoingHome(true);
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
            this.notifyLarvaeSubscribersForAdd();
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
        this.notifyLarvaeSubscribersForDrop();
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
    public void attack(Ant victim) {
        // victim.decreaseHealth(this.strength);

        AttackCommand attackRequest = new AttackCommand(this, victim);
        this.sendCommandAndAwaitExecution(attackRequest);
    }

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
    public String info() {
        StringBuffer sb = new StringBuffer( super.info() );
        sb.append("Number of Larvae: " + this.numberOfLarvaes.get() + "\n");
        return sb.toString();
    }
}
