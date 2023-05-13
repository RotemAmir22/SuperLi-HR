package ControllerLayer;

import BussinesLogic.Driver;
import DataAccessLayer.TransitDAO;
import DomainLayer.OrderDocument;
import DomainLayer.Transit;
import DomainLayer.Truck;
import ExceptionsPackage.QualificationsException;
import ExceptionsPackage.UiException;

import java.sql.SQLException;
import java.util.Set;

public interface TransitController {
    Transit createTransit(String dateString, String truckPlateNumber, String driverId) throws UiException, QualificationsException, SQLException;
    Transit findTransitByID(int transitId);
    Set<Transit> getTransitsSet();
    TransitDAO getTransitDAO();
    boolean showTransitByID(int transitId);
    int replaceTransitTruck(int transitId, String truckPlate);
    int replaceTransitDriver(int transitId, String driverId, String truckPlate) throws SQLException;
    OrderDocumentController getOrderDocController();
    TransitRecordController getTransitRecordController();
    void moveTransitToFinished(Transit transit);
    boolean transferLoad(Truck smallTruck, Truck biggerTruck);
    boolean isDriverAllowToDriveTruck(Truck truck, Driver driver);
    //TODO belongs to check weight before starting transit.
    boolean isValidWeight(Transit currentTransit, OrderDocument orderDocument);
}
