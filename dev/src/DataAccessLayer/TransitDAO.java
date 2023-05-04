package DataAccessLayer;

import DomainLayer.Transit;

import java.util.Set;

public interface TransitDAO {
    void saveTransit(Transit transit);
    void removeTransit(Transit transit);
    Set<Transit> getTransitsSet();
    Transit findTransitByID(int transitId);
    void moveToCompleted(Transit completedTransit);
}
