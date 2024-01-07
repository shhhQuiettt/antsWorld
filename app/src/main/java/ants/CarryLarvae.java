package ants;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * picksLarve
 */
public interface CarryLarvae {
    public AtomicInteger getNumberOfLarvaes();

    public boolean isCarryingLarvae();

    public void tryToPickLarve();

    public void dropLarve();

    // public void addLarve();

    // public decrementLarves();

}
