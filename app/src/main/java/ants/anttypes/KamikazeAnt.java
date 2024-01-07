package ants.anttypes;

import ants.commands.Command;
import ants.commands.ExplosionCommand;
import ants.interfaces.Attacking;
import ants.map.Anthill;

/**
 * KamikazeAnt
 */
public class KamikazeAnt extends Ant implements Attacking {
    public KamikazeAnt(String name, int health, int strength, Anthill initialAnthill, Anthill enemyAnthill) {
        super(name, AntColor.BLUE, health, strength, initialAnthill, enemyAnthill);
    }

    @Override
    protected void doActionsInVertex() {
        this.tryToAttack();

        this.doScanning();

        this.tryToAttack();
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

    public Ant getEnemy() {
        if (currentVertex.redAntInVertex()) {
            return currentVertex.getRedAnt();
        }
        return null;
    }

    @Override
    public void attack(Ant victim) {
        this.setState(AntState.ATTACKING);
        Command attackRequest = new ExplosionCommand(this);
        this.sendCommandAndAwaitExecution(attackRequest);
    }

    // @Override
    // protected void beforeDie() {
    //     if (this.isExploding) {
    //         return;
    //     }

    //     this.attack(null);
    // }
}
