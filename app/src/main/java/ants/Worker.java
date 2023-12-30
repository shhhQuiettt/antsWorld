package ants;

/**
 * CollectorAnt
 */
public class Worker extends Ant implements carryLarvae, Attacking {
    private int numberOfLarvaes = 0;

    public Worker(String name, int health, int strength, Anthill initialAnthill) {
        super(name, Color.BLUE, health, strength, initialAnthill);
    }

    public int getNumberOfLarvaes() {
        return this.numberOfLarvaes;
    }

    public Ant getAndLockEnemy() {
        while (currentVertex.blueAntInVertex()) {
            Ant victim = currentVertex.getBlueAnt();
            if (victim == null) {
                return null;
            }

            victim.mutex.lock();
            if (victim == currentVertex.getBlueAnt()) {
                return victim;
            }
            victim.mutex.unlock();
        }
        return null;
    }

    @Override
    protected void doActions() {
        Ant victim = getAndLockEnemy();
        if (victim != null) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("Error while waiting in SoldierAnt");
            }
            this.attack(victim);
            victim.mutex.unlock();
            this.setGoingHome(true);
        }

        if (this.numberOfLarvaes < this.strength && this.currentVertex.tryToGetLarve()) {
            this.pickLarve();
            if (this.numberOfLarvaes == this.strength) {
                this.setGoingHome(true);
            }
        }

        this.state = AntState.MOVING;
        this.goToNextVertex();
        this.setState(AntState.SCANNING);

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.err.println("Error while waiting in SoldierAnt");
        }
    }

    @Override
    public void pickLarve() {
        this.numberOfLarvaes++;
    }

    @Override
    public void dropLarve() {
        this.numberOfLarvaes--;
        this.currentVertex.putLarve();
    }

    @Override
    protected void die() {
        this.currentVertex.putLarve();
        this.state = AntState.DYING;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("While waiting for ant to die, Ant was interrupted");
        }

        this.state = AntState.DEAD;

        this.currentVertex.removeAnt(this);
    }

    @Override
    public void attack(Ant victim) {
        victim.decreaseHealth(this.strength);
    }

}
