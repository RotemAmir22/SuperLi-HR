package DataAccessLayer;

import DomainLayer.Transit;
import DomainLayer.TransitRecord;

import java.util.HashSet;
import java.util.Set;

public class TransitRecordsRepositoryImpl implements TransitRecordRepository{
    final private Set<TransitRecord> records = new HashSet<>();

    @Override
    public void saveTransitRecord(TransitRecord transitRecord) {
        records.add(transitRecord);
    }

    @Override
    public void removeTransitRecord(TransitRecord transitRecord) {
        records.remove(transitRecord);
    }

    @Override
    public Set<TransitRecord> getTransitRecordsSet() {
        return records;
    }

    @Override
    public TransitRecord findTransitRecordByID(int transitRecordId) {
        for(TransitRecord transitRecord : records)
        {
            if(transitRecord.getTransitRecordId() == transitRecordId)
                return transitRecord;
        }
        return null;
    }
}
