package ControllerServiceLayer;

import DataAccessLayer.TransitRepository;
import DomainLayer.Transit;
import DomainLayer.Truck;
import ExceptionsPackage.UiException;

import java.text.ParseException;
import java.util.Set;

public interface TransitService {
    Transit createTransit(String dateString, String truckPlateNumber, int driverId) throws UiException;
    boolean removeTransitById(int transitId);
    Transit findTransitByID(int transitId);
    Set<Transit> getTransitsSet();
    TransitRepository getTransitRepo();
    void showAllTransits();
    boolean showTransitByID(int transitId);
    boolean setTransitTruck(Transit transit, String truckPlate);
}
