package ControllerServiceLayer;

import DataAccessLayer.TransitRecordRepository;
import DataAccessLayer.TransitRepository;
import DomainLayer.OrderDocument;
import DomainLayer.Transit;
import DomainLayer.TransitRecord;
import ExceptionsPackage.QualificationsException;
import ExceptionsPackage.UiException;

import java.util.Set;

public interface TransitRecordService {
    TransitRecord createTransitRecord(Transit transit);
    TransitRecordRepository getTransitRecordRepo();
    public void showTransitRecords();

}
