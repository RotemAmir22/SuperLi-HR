package DataAccessLayer;

import DomainLayer.Transit;
import DomainLayer.TransitRecord;

import java.util.Set;

public interface TransitRecordRepository {
    void saveTransitRecord(TransitRecord transitRecord);
    void removeTransitRecord(TransitRecord transitRecord);
    Set<TransitRecord> getTransitRecordsSet();
    TransitRecord findTransitRecordByID(int transitRecordId);
}
