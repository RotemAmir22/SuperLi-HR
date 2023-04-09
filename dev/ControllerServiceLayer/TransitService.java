package ControllerServiceLayer;

import DataAccessLayer.TransitRepository;
import DomainLayer.Transit;
import ExceptionsPackage.QualificationsException;
import ExceptionsPackage.UiException;

import java.util.Set;

public interface TransitService {
    Transit createTransit(String dateString, String truckPlateNumber, int driverId) throws UiException, QualificationsException;
    boolean removeTransitById(int transitId);
    Transit findTransitByID(int transitId);
    Set<Transit> getTransitsSet();
    TransitRepository getTransitRepo();
    void showAllTransits();
    boolean showTransitByID(int transitId);
    int replaceTransitTruck(int transitId, String truckPlate);
    int replaceTransitDriver(int transitId, int driverId, String truckPlate);
}
