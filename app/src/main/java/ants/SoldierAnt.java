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
        // AttackCommand attackRequest = new AttackCommand(victim);
        // this.sendCommand(attackRequest);
        victim.decreaseHealth(this.strength);
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
            this.setState(AntState.ATTACKING);
            System.out.println(this.color + " Soldier is attacking " + victim.color + " ant");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("Error while waiting in SoldierAnt");
            }
            this.attack(victim);
            victim.mutex.unlock();
            this.setGoingHome(true);
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
}
