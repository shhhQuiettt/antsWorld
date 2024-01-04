package ants;

import java.lang.Thread;

/**
 * SoldierAnt
 */
public class SoldierAnt extends Ant implements Attacking {
    public SoldierAnt(String name, int health, int strength, Anthill initialAnthill) {
        super(name, Color.RED, health, strength, initialAnthill);
    }

    @Override
    public void attack(Ant victim) {
        System.out.println(this.color + " Soldier is issuing attack on" + victim.color + " ant");
        AttackCommand attackRequest = new AttackCommand(this, victim);
        this.sendCommandAndWait(attackRequest);
        // victim.decreaseHealth(this.strength);
    }

    // public Ant getAndLockEnemy() {
    // while (currentVertex.blueAntInVertex()) {
    // System.out.println(this.color + " Soldier entered while loop");
    // Ant victim = currentVertex.getBlueAnt();
    // if (victim == null) {
    // System.out.println(this.color + " Soldier FOUND a blue in vertex but it
    // disappeared");
    // return null;
    // }

    // victim.mutex.lock();
    // System.out.println(this.color + " Soldier is locking " + victim.color + "
    // ant");
    // if (victim == currentVertex.getBlueAnt()) {
    // System.out.println(this.color + " Soldier is returnign " + victim.color + "
    // ant");
    // return victim;
    // }
    // victim.mutex.unlock();
    // }
    // System.out.println(this.color + " Soldier did not found any blue in vertex");
    // return null;
    // }

    public Ant getEnemy() {
        if (currentVertex.blueAntInVertex()) {
            return currentVertex.getBlueAnt();
        }
        return null;
    }

    @Override
    protected void doActionsInVertex() {

        // Ant victim = getAndLockEnemy();
        // if (victim != null) {
        // this.setState(AntState.ATTACKING);
        // System.out.println(this.color + " Soldier is attacking " + victim.color + "
        // ant");
        // try {
        // Thread.sleep(1000);
        // } catch (Exception e) {
        // System.err.println("Error while waiting in SoldierAnt");
        // }
        // this.attack(victim);
        // victim.mutex.unlock();
        // this.setGoingHome(true);
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
}
