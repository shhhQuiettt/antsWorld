package ants;

/**
 * AttackCommand
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

    private void onReturn() {
        this.unlockVictim();
        this.executionSemaphore.release();
    }

    @Override
    protected boolean execute() {
        System.out.println("AttackCommand is executing. Victim is " + victim.getName() + "");

        if (!tryLockVictim()) {
            System.out.println("lock failed");
            onReturn();
            return false;
        }

        if (!this.attacker.isAlive() || !this.victim.isAlive() ) {
            System.out.println("Atacker or Victim is already dead");
            onReturn();
            return false;
        }

        victim.die();

        onReturn();
        return true;
    }

}
