package ControllerLayer;

import DataAccessLayer.TransitDAO;
import DomainLayer.OrderDocument;
import DomainLayer.Transit;
import DomainLayer.Truck;
import ExceptionsPackage.QualificationsException;
import ExceptionsPackage.UiException;

import java.util.Set;

public interface TransitController {
    Transit createTransit(String dateString, String truckPlateNumber, String driverId) throws UiException, QualificationsException;
    Transit findTransitByID(int transitId);
    Set<Transit> getTransitsSet();
    TransitDAO getTransitDAO();
    boolean showTransitByID(int transitId);
    int replaceTransitTruck(int transitId, String truckPlate);
    int replaceTransitDriver(int transitId, String driverId, String truckPlate);
    OrderDocumentController getOrderDocController();
    TransitRecordController getTransitRecordController();
    void moveTransitToFinished(Transit transit);
    boolean isValidWeight(Transit currentTransit, OrderDocument orderDocument);
    //boolean isDriverAllowToDriveTruck(Truck truck, Driver driver);
    boolean transferLoad(Truck smallTruck, Truck biggerTruck);
}
