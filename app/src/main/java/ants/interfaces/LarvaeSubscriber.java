package ants.interfaces;

/**
 * LarvaeSubscriber
 *
 * Observer notified when a larvae is added or removed
 */
public interface LarvaeSubscriber {
    public void onLarvaeAdded();

    public void onLarvaeRemoved();
}
