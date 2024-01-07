package ants.interfaces;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CarryLarvae
 *
 * Entity able to carry larvae.
 */
public interface CarryLarvae {
    public AtomicInteger getNumberOfLarvaes();

    public boolean isCarryingLarvae();

    public void tryToPickLarve();

    public void dropLarve();
}
