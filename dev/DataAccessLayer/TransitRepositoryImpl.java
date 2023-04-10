package DataAccessLayer;

import DomainLayer.Transit;

import java.util.HashSet;
import java.util.Set;

public class TransitRepositoryImpl implements TransitRepository{
    final private Set<Transit> transits = new HashSet<>();
    final private Set<Transit> completedTransits = new HashSet<>();
    @Override
    public void saveTransit(Transit transit) {
        transits.add(transit);
    }
    @Override
    public void removeTransit(Transit transit) {
        transits.remove(transit);
    }
    @Override
    public Set<Transit> getTransitsSet() {
        return transits;
    }
    @Override
    public Transit findTransitByID(int transitId) {
        for (Transit transit : transits)
        {
            if (transit.getTransitId() == transitId)
                return transit;
        }
        return null;
    }
    public void saveToCompleted(Transit completedTransit){
        completedTransits.add(completedTransit);
    }
}
