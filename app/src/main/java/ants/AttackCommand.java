package ants;

/**
 * AttackCommand
 */
public class AttackCommand extends Command{
    public Ant[] getInvolvedAnts() {
        return new Ant[]{victim};
    }
    private Ant victim;

    public AttackCommand(Ant victim) {
        this.victim = victim;
    }

    @Override
    protected void execute() {
        if (victim.getState() == AntState.DYING || victim.getState() == AntState.DEAD) {
            return;
        }

        victim.setState(AntState.DYING);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.err.println("Error while waiting in AttackCommand");
        }

        victim.setState(AntState.DEAD);

        Vertex victimVertex = victim.getCurrentVertex();
        victimVertex.removeAnt(victim);

        victim.die();
    }
}
