package ants.commands;

import ants.anttypes.Ant;

/**
 * AttackCommand
 *
 * Command that is used to attack an enemy ant
 */

public class AttackCommand extends Command {
    private Ant victim;
    private Ant attacker;

    public AttackCommand(Ant attacker, Ant victim) {
        this.attacker = attacker;
        this.victim = victim;
    }

    private boolean tryLockVictim() {
        return victim.leaveVertexSemaphore.tryAcquire();
    }

    private void unlockVictim() {
        victim.leaveVertexSemaphore.release();
    }

    @Override
    public boolean execute() {
        if (!tryLockVictim()) {
            this.executionSemaphore.release();
            return false;
        }

        if (!this.attacker.isAlive() || !this.victim.isAlive() || this.victim.isHidden()) {
            this.unlockVictim();
            this.executionSemaphore.release();
            return false;
        }

        victim.die();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("While waiting for attack to finish, Thread was interrupted");
        }

        this.executionSemaphore.release();
        return true;
    }

}
