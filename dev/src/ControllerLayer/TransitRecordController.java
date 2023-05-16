package ControllerLayer;

import DataAccessLayer.TransitRecordDAO;
import DomainLayer.Transit;
import DomainLayer.TransitRecord;

public interface TransitRecordController {
    TransitRecord createTransitRecord(Transit transit);
    TransitRecordDAO getTransitRecordDAO();
    void saveTransitRecord(TransitRecord transitRecord);
    void showTransitRecords();

}
