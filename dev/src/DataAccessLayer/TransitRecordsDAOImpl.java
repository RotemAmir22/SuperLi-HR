package DataAccessLayer;

import DataAccess.Database;
import DomainLayer.Supplier;
import DomainLayer.Transit;
import DomainLayer.TransitRecord;

import java.sql.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TransitRecordsDAOImpl implements TransitRecordDAO {
    final private Set<TransitRecord> recordsSet = new HashSet<>();
    final private TransitDAO transitDAO;
    final private SupplierDAO supplierDAO;
    private final Connection connection;

    public TransitRecordsDAOImpl(Connection connection, TransitDAO transitDAO, SupplierDAO supplierDAO) throws SQLException, ClassNotFoundException {
        this.connection = connection;
        this.transitDAO = transitDAO;
        this.supplierDAO = supplierDAO;
    }

    public void saveTransitRecord(TransitRecord transitRecord) {
        String insertTransitRecordQuery = "INSERT INTO TransitRecords (transitRecordId, transitId, isOverWeight) VALUES (?, ?, ?)";
        String insertWeightAtExitQuery = "INSERT INTO TransitRecordsWeightAtExitSupplier (transitRecordId, supplierId, weightAtExit) VALUES (?, ?, ?)";

        try {
            PreparedStatement transitRecordStatement = connection.prepareStatement(insertTransitRecordQuery);
            transitRecordStatement.setInt(1, transitRecord.getTransitRecordId());
            transitRecordStatement.setInt(2, transitRecord.getTransit().getTransitId());
            transitRecordStatement.setBoolean(3, transitRecord.isTransitProblem());
            transitRecordStatement.executeUpdate();

            for (Map.Entry<Supplier, Double> entry : transitRecord.getWeightsAtExits().entrySet()) {
                Supplier supplier = entry.getKey();
                double weight = entry.getValue();

                PreparedStatement weightAtExitStatement = connection.prepareStatement(insertWeightAtExitQuery);
                weightAtExitStatement.setInt(1, transitRecord.getTransitRecordId());
                weightAtExitStatement.setInt(2, supplier.getSupplierId());
                weightAtExitStatement.setDouble(3, weight);
                weightAtExitStatement.executeUpdate();
            }

            recordsSet.add(transitRecord);
        } catch (SQLException e) {
            System.out.println("Error saving transit record to the database: " + e.getMessage());
        }
    }

    @Override
    public TransitRecord findTransitRecordByID(int transitRecordId) {
        for (TransitRecord transitRecord : recordsSet) {
            if (transitRecord.getTransitRecordId() == transitRecordId) {
                return transitRecord;
            }
        }

        String selectTransitRecordQuery = "SELECT transitId, isOverWeight FROM TransitRecords WHERE transitRecordId = ?";
        String selectWeightAtExitQuery = "SELECT supplierId, weightAtExit FROM TransitRecordsWeightAtExitSupplier WHERE transitRecordId = ?";
        try {
            PreparedStatement transitRecordStatement = connection.prepareStatement(selectTransitRecordQuery);
            transitRecordStatement.setInt(1, transitRecordId);
            ResultSet transitRecordResultSet = transitRecordStatement.executeQuery();

            if (transitRecordResultSet.next()) {
                int transitId = transitRecordResultSet.getInt("transitId");
                boolean isOverWeight = transitRecordResultSet.getBoolean("isOverWeight");

                Transit transit = transitDAO.findTransitByID(transitId);
                TransitRecord transitRecord = new TransitRecord(transitRecordId, transit, isOverWeight);

                PreparedStatement weightAtExitStatement = connection.prepareStatement(selectWeightAtExitQuery);
                weightAtExitStatement.setInt(1, transitRecordId);
                ResultSet weightAtExitResultSet = weightAtExitStatement.executeQuery();

                while (weightAtExitResultSet.next()) {
                    int supplierId = weightAtExitResultSet.getInt("supplierId");
                    double weightAtExit = weightAtExitResultSet.getDouble("weightAtExit");
                    Supplier supplier = supplierDAO.findSupplierById(supplierId);
                    transitRecord.getWeightsAtExits().put(supplier, weightAtExit);
                }

                return transitRecord;
            }
        } catch (SQLException e) {
            System.out.println("Error finding transit record in the database: " + e.getMessage());
        }

        return null; // If transit record is not found, return null or handle accordingly
    }

    @Override
    public Set<TransitRecord> getTransitRecordsSet() {
        Set<TransitRecord> recordsSet = new HashSet<>();
        String selectTransitRecordQuery = "SELECT * FROM TransitRecords";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectTransitRecordQuery);

            while (resultSet.next()) {
                int transitRecordId = resultSet.getInt("transitRecordId");
                TransitRecord transitRecord = findTransitRecordByID(transitRecordId);
                recordsSet.add(transitRecord);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving trucks from database: " + e.getMessage());
        }

        return recordsSet;
    }


}
