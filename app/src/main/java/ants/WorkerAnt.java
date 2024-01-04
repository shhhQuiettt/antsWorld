package ants;

/**
 * CollectorAnt
 */
public class WorkerAnt extends Ant implements carryLarvae, Attacking {
    private int numberOfLarvaes = 0;

    public WorkerAnt(String name, int health, int strength, Anthill initialAnthill) {
        super(name, Color.BLUE, health, strength, initialAnthill);
    }

    public int getNumberOfLarvaes() {
        return this.numberOfLarvaes;
    }

    public Ant getEnemy() {
        if (currentVertex.redAntInVertex()) {
            return currentVertex.getRedAnt();
        }
        return null;
    }

    public boolean isCarryingLarvae() {
        return this.numberOfLarvaes > 0;
    }

    // public Ant getAndLockEnemy() {
    // while (currentVertex.redAntInVertex()) {
    // System.out.println(this.color + " Worker entered while loop");
    // Ant victim = currentVertex.getRedAnt();
    // if (victim == null) {
    // System.out.println(this.color + " Worker FOUND a red in vertex but it
    // disappeared");
    // return null;
    // }

    // victim.mutex.lock();
    // System.out.println(this.color + " Worker is locking " + victim.color + "
    // ant");
    // if (victim == currentVertex.getRedAnt()) {
    // System.out.println(this.color + " Worker is returnign " + victim.color + "
    // ant");
    // return victim;
    // }
    // victim.mutex.unlock();
    // }
    // System.out.println(this.color + " Worker did not found any red in vertex");
    // return null;
    // }

    @Override
    protected void doActionsInVertex() {
        // Ant victim = getAndLockEnemy();
        // if (victim != null) {
        // try {
        // Thread.sleep(1000);
        // } catch (Exception e) {
        // System.err.println("Error while waiting in SoldierAnt");
        // }
        // this.attack(victim);
        // victim.mutex.unlock();
        // this.setGoingHome(true);
        // }

        // if (this.numberOfLarvaes < this.strength &&
        // this.currentVertex.tryToGetLarve()) {
        // this.pickLarve();
        // if (this.numberOfLarvaes == this.strength) {
        // this.setGoingHome(true);
        // }
        // }
        Ant potentialVictim = this.getEnemy();
        if (potentialVictim != null) {
            this.setState(AntState.ATTACKING);
            // try {
            // Thread.sleep(1000);
            // } catch (Exception e) {
            // System.err.println("Error while waiting in SoldierAnt");
            // }
            this.attack(potentialVictim);
            // this.setGoingHome(true);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting in SoldierAnt");
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void pickLarve() {
        this.numberOfLarvaes++;
    }

    @Override
    public void dropLarve() {
        if (this.numberOfLarvaes == 0)
            throw new IllegalStateException("Cannot drop larve when not carrying any");

        this.numberOfLarvaes--;
        this.currentVertex.putLarve();
    }

    @Override
    protected void beforeDie() {
        if (this.isCarryingLarvae())
            this.dropLarve();
    }

    @Override
    public void attack(Ant victim) {
        // victim.decreaseHealth(this.strength);

        System.out.println(this.color + " Worker is issuing attack " + victim.color + " ant");
        AttackCommand attackRequest = new AttackCommand(this, victim);
        this.sendCommandAndWait(attackRequest);
    }

}
