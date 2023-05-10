package DataAccessLayer;

import DataAccess.Database;
import DomainLayer.License;
import DomainLayer.Truck;
import DomainLayer.TruckModel;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class TruckDAOImpl implements TruckDAO {
    private final Set<Truck> trucksSet = new HashSet<>();
    private final Connection connection;

    public TruckDAOImpl() throws SQLException, ClassNotFoundException {
        connection = Database.connect();
    }

    @Override
    public void saveTruck(Truck truck) {
        String insertTruckSQL = "INSERT INTO trucks (plateNumber, model, truckWeight, maxCarryWeight) VALUES (?, ?, ?, ?)";
        String insertTruckLicensesSQL = "INSERT INTO TruckLicenses (plateNumber, licenses) VALUES (?, ?)";
        try {
            PreparedStatement insertTruckStatement = connection.prepareStatement(insertTruckSQL);
            insertTruckStatement.setString(1, truck.getPlateNumber());
            insertTruckStatement.setString(2, truck.getModel().toString());
            insertTruckStatement.setDouble(3, truck.getTruckWeight());
            insertTruckStatement.setDouble(4, truck.getMaxCarryWeight());
            insertTruckStatement.executeUpdate();

            PreparedStatement insertTruckQualificationStatement = connection.prepareStatement(insertTruckLicensesSQL);

            for (License license : truck.getTruckLicenses()) {
                insertTruckQualificationStatement.setString(2, license.toString());
                insertTruckQualificationStatement.executeUpdate();
            }
            trucksSet.add(truck);
        } catch (SQLException e) {
            System.out.println("Error saving truck to database: " + e.getMessage());
        }
    }
    @Override
    public void removeTruck(Truck truck) {
        String deleteTruckSQL = "DELETE FROM trucks WHERE plateNumber=?";
        try {
            PreparedStatement deleteTruckStatement = connection.prepareStatement(deleteTruckSQL);
            deleteTruckStatement.setString(1, truck.getPlateNumber());
            deleteTruckStatement.executeUpdate();
            removeTruckFromTrucksLicenses(truck);

            trucksSet.remove(truck);
        } catch (SQLException e) {
            System.out.println("Error removing truck from database: " + e.getMessage());
        }
    }
    // TODO verify this function works properly
    @Override
    public void removeTruckFromTrucksLicenses(Truck truck) {
        String deleteTruckLicensesSQL = "DELETE FROM TruckLicenses WHERE plateNumber=?";
        try {
            PreparedStatement deleteTruckLicensesStatement = connection.prepareStatement(deleteTruckLicensesSQL);
            deleteTruckLicensesStatement.setString(1, truck.getPlateNumber());
            deleteTruckLicensesStatement.executeUpdate();
            trucksSet.remove(truck);
        } catch (SQLException e) {
            System.out.println("Error removing truck from database: " + e.getMessage());
        }
    }


    @Override
    public Truck findTruckByPlateNumber(String tPlateNumber) {
        // First, check if truck is already in the set
        for (Truck truck : trucksSet) {
            if (truck.getPlateNumber().equals(tPlateNumber)) {
                return truck;
            }
        }

        // If not found in the set, query the database
        String selectSQL = "SELECT * FROM trucks WHERE plateNumber = ?";
        try {
            PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
            selectStatement.setString(1, tPlateNumber);
            ResultSet result = selectStatement.executeQuery();

            if (result.next()) {
                String plateNumber = result.getString("plateNumber");
                String modelStr = result.getString("model");
                TruckModel model = TruckModel.valueOf(modelStr);
                double truckWeight = result.getDouble("truckWeight");
                double maxCarryWeight = result.getDouble("maxCarryWeight");

                // Retrieve the truck's licenses from the TruckLicenses table
                String selectLicensesSQL = "SELECT * FROM TruckLicenses WHERE plateNumber = ?";
                PreparedStatement selectLicensesStatement = connection.prepareStatement(selectLicensesSQL);
                selectLicensesStatement.setString(1, plateNumber);
                ResultSet licensesResult = selectLicensesStatement.executeQuery();
                Set<License> licensesList = new HashSet<>();
                while (licensesResult.next()) {
                    String licenseStr = licensesResult.getString("licenses");
                    License license = License.valueOf(licenseStr);
                    licensesList.add(license);
                }

                Truck truck = new Truck(plateNumber, model, truckWeight, maxCarryWeight, licensesList);
                trucksSet.add(truck); // Add truck to set for future lookups
                return truck;
            }

            // Truck not found in database
            return null;
        } catch (SQLException e) {
            System.out.println("Error finding truck in database: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Set<Truck> getTrucksSet() {
        Set<Truck> trucksSet = new HashSet<>();
        String selectTrucksSQL = "SELECT * FROM trucks";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectTrucksSQL);

            while (resultSet.next()) {
                String plateNumber = resultSet.getString("plateNumber");
                Truck truck = findTruckByPlateNumber(plateNumber);
                // TODO main Truck set is getting full also ? thinks about this
                trucksSet.add(truck);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving trucks from database: " + e.getMessage());
        }

        return trucksSet;
    }
}
