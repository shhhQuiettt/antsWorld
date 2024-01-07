package ants.anttypes;

import java.lang.Thread;

import ants.commands.AttackCommand;
import ants.interfaces.Attacking;
import ants.map.Anthill;

/**
 * SoldierAnt
 */
public class SoldierAnt extends Ant implements Attacking {
    public SoldierAnt(String name, int health, int strength, Anthill initialAnthill, Anthill enemyAnthil) {
        super(name, AntColor.RED, health, strength, initialAnthill, enemyAnthil);
    }

    @Override
    public void attack(Ant victim) {
        AttackCommand attackRequest = new AttackCommand(this, victim);
        this.sendCommandAndAwaitExecution(attackRequest);
    }

    public Ant getEnemy() {
        if (currentVertex.blueAntInVertex()) {
            return currentVertex.getBlueAnt();
        }
        return null;
    }

    private void tryToAttack() {
        Ant potentialVictim = this.getEnemy();
        while (potentialVictim != null) {
            this.setState(AntState.ATTACKING);
            this.attack(potentialVictim);
            this.setState(AntState.SCANNING);
            this.setGoingHome(true);
            potentialVictim = this.getEnemy();
        }
    }


    @Override
    protected void doActionsInVertex() {
        this.tryToAttack();

        this.tryToHide();

        this.doScanning();

        this.tryToAttack();

        if (this.isHidden()) {
            this.unhide();
        }
    }
}
