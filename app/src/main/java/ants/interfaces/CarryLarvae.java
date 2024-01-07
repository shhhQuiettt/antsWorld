package ants.interfaces;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * picksLarve
 */
public interface CarryLarvae {
    public AtomicInteger getNumberOfLarvaes();

    public boolean isCarryingLarvae();

    public void tryToPickLarve();

    public void dropLarve();
}
