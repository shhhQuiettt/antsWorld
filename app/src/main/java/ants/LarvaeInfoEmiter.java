package ants;

/**
 * LarvaeInfoEmiter
 */
public interface LarvaeInfoEmiter {
    public void subscribeLarvae(LarvaeSubscriber subscriber);

    public void unsubscribeLarvae(LarvaeSubscriber subscriber);
}
