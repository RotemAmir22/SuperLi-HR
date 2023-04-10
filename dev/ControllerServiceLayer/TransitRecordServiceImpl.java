package ControllerServiceLayer;

import DataAccessLayer.TransitRecordRepository;
import DomainLayer.Transit;
import DomainLayer.TransitRecord;
import java.util.Set;

public class TransitRecordServiceImpl implements TransitRecordService{

    private final TransitRecordRepository transitRecordRepo;

    public TransitRecordServiceImpl(TransitRecordRepository transitRecordRepo) {
        this.transitRecordRepo = transitRecordRepo;
    }

    @Override
    public TransitRecord createTransitRecord(Transit transit) {
        TransitRecord newTransitRecord = new TransitRecord(transit);
        return newTransitRecord;
    }

    @Override
    public TransitRecordRepository getTransitRecordRepo() {
        return transitRecordRepo;
    }
    @Override
    public void showTransitRecords(){
        System.out.println("-----Transit Records-----");
        Set<TransitRecord> transitRecordSet = transitRecordRepo.getTransitRecordsSet();
        for (TransitRecord transitRecord: transitRecordSet){
            transitRecord.printTransitRecord();
        }
    }
}
