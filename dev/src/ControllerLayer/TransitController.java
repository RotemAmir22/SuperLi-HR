package ControllerLayer;

import BussinesLogic.Driver;
import DataAccessLayer.TransitDAO;
import DomainLayer.OrderDocument;
import DomainLayer.Transit;
import DomainLayer.Truck;
import ExceptionsPackage.UiException;
import java.util.Set;

public interface TransitController {
    Transit createTransit(String dateString, String truckPlateNumber, String driverId) throws UiException;
    Transit findTransitByID(int transitId);
    TransitDAO getTransitDAO();
    boolean showTransitByID(int transitId);
    int replaceTransitTruck(int transitId, String truckPlate);
    int replaceTransitDriver(int transitId, String driverId, String truckPlate);
    OrderDocumentController getOrderDocController();
    TransitRecordController getTransitRecordController();
    void moveTransitToFinished(Transit transit);
    boolean transferLoad(Truck smallTruck, Truck biggerTruck);
    boolean isDriverAllowToDriveTruck(Truck truck, Driver driver);

//    Set<Transit> getTransitsSet();
    //TODO belongs to check weight before starting transit.
    boolean isValidWeight(Transit currentTransit, OrderDocument orderDocument);
}
