package ants;

/**
 * KamikazeAnt
 */
public class KamikazeAnt extends Ant implements Attacking {
    private boolean isExploding = false;

    public KamikazeAnt(String name, int health, int strength, Anthill initialAnthill, Anthill enemyAnthill) {
        super(name, AntColor.BLUE, health, strength, initialAnthill, enemyAnthill);
    }

    @Override
    protected void doActionsInVertex() {
        this.tryToAttack();

        try {
            Thread.sleep(getRandomWaitingTime());
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting in SoldierAnt");
            Thread.currentThread().interrupt();
        }

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
        this.isExploding = true;

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
