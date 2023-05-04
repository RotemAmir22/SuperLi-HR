package DataAccessLayer;

import DomainLayer.Transit;

import java.util.HashSet;
import java.util.Set;

public class TransitDAOImpl implements TransitDAO {
    final private Set<Transit> pendingTransitsSet = new HashSet<>();
    final private Set<Transit> completedTransitsSet = new HashSet<>();


    @Override
    public void saveTransit(Transit transit) {
        pendingTransitsSet.add(transit);
    }
    @Override
    public void removeTransit(Transit transit) {
        pendingTransitsSet.remove(transit);
    }
    @Override
    public Set<Transit> getTransitsSet() {
        return pendingTransitsSet;
    }
    @Override
    public Transit findTransitByID(int transitId) {
        for (Transit transit : pendingTransitsSet)
        {
            if (transit.getTransitId() == transitId)
                return transit;
        }
        return null;
    }
    public void moveToCompleted(Transit completedTransit){
        pendingTransitsSet.remove(completedTransit);
        completedTransitsSet.add(completedTransit);
    }
}
