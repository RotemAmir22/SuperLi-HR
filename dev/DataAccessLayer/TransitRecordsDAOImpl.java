package DataAccessLayer;

import DomainLayer.TransitRecord;

import java.util.HashSet;
import java.util.Set;

public class TransitRecordsDAOImpl implements TransitRecordDAO {
    final private Set<TransitRecord> recordsSet = new HashSet<>();


    @Override
    public void saveTransitRecord(TransitRecord transitRecord) {
        recordsSet.add(transitRecord);
    }
    @Override
    public void removeTransitRecord(TransitRecord transitRecord) {
        recordsSet.remove(transitRecord);
    }
    @Override
    public Set<TransitRecord> getTransitRecordsSet() {
        return recordsSet;
    }
    @Override
    public TransitRecord findTransitRecordByID(int transitRecordId) {
        for(TransitRecord transitRecord : recordsSet)
        {
            if(transitRecord.getTransitRecordId() == transitRecordId)
                return transitRecord;
        }
        return null;
    }
}
