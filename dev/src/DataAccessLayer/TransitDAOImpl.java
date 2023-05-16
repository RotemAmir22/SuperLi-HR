package DataAccessLayer;

import BussinesLogic.BranchStore;
import DataAccess.DAO_BranchStore;
import DataAccess.DAO_Employee;
import DataAccess.Database;
import DomainLayer.OrderDocument;
import DomainLayer.Supplier;
import DomainLayer.Transit;
import DomainLayer.Truck;
import BussinesLogic.Driver;
import java.sql.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransitDAOImpl implements TransitDAO {
    final private Set<Transit> pendingTransitsSet = new HashSet<>();
    final private Set<Transit> completedTransitsSet = new HashSet<>();
    private final Connection connection;
    private final TruckDAO truckDAO;
    private final DAO_Employee driverDAO;
    private final OrderDocumentDAO orderDocumentDAO;
    private final SupplierDAO supplierDAO; // TODO when merge with suppliers module see also branch store
    private final DAO_BranchStore branchStoreDAO; //TODO check if can create here instead of passing as parameter
    public TransitDAOImpl(TruckDAO truckDAO, DAO_Employee daoEmployee,OrderDocumentDAO orderDocumentDAO ,SupplierDAO supplierDAO , DAO_BranchStore branchStoreDAO) throws SQLException, ClassNotFoundException {
        this.connection = Database.connect();
        this.truckDAO = truckDAO;
        this.driverDAO = daoEmployee;
        this.orderDocumentDAO = orderDocumentDAO;
        this.supplierDAO = supplierDAO;
        this.branchStoreDAO = branchStoreDAO;
    }

    public void saveTransit(Transit transit) {
        String insertTransitQuery = "INSERT INTO Transits (transitDate, truckPlateNumber, driverId) VALUES (?, ?, ?)";
        try {
            // Insert the transit data into the Transits table
            PreparedStatement insertTransitStmt = connection.prepareStatement(insertTransitQuery);
            insertTransitStmt.setString(1, transit.getTransitDate().toString());
            insertTransitStmt.setString(2, transit.getTruck().getPlateNumber());
            //TODO driverID is String - decide on convention! - at Drivers table is int.
            // TODO also what was decided about driver? is chosen in the creation of a new transit?
            insertTransitStmt.setInt(3, Integer.parseInt(transit.getDriver().getId()));
            insertTransitStmt.executeUpdate();
            // Get the generated transitId
//            int transitId = 0;
//            String getTransitIdQuery = "SELECT LAST_INSERT_ID()";
//            PreparedStatement getTransitIdStmt = connection.prepareStatement(getTransitIdQuery);
//            ResultSet resultSet = getTransitIdStmt.executeQuery();
//            if (resultSet.next()) {
//                transitId = resultSet.getInt(1);
//            }

            // Insert transitId and orderDocumentId into TransitOrderDocuments table
//            String insertTransitOrderDocQuery = "INSERT INTO TransitOrderDocuments (transitId, orderDocumentId) VALUES (?, ?)";
//            PreparedStatement insertTransitOrderDocStmt = connection.prepareStatement(insertTransitOrderDocQuery);
//            insertTransitOrderDocStmt.setInt(1, transitId);
//            insertTransitOrderDocStmt.setInt(2, transit.getOrderDocumentId());
//            insertTransitOrderDocStmt.executeUpdate();
//
//            // Insert transitId, supplierId, and orderDocumentId into TransitSuppliersRoute table
//            String insertTransitSuppliersRouteQuery = "INSERT INTO TransitSuppliersRoute (transitId, supplierId, orderDocumentId) VALUES (?, ?, ?)";
//            PreparedStatement insertTransitSuppliersRouteStmt = connection.prepareStatement(insertTransitSuppliersRouteQuery);
//            insertTransitSuppliersRouteStmt.setInt(1, transitId);
//            insertTransitSuppliersRouteStmt.setInt(2, transit.getSupplierId());
//            insertTransitSuppliersRouteStmt.setInt(3, transit.getOrderDocumentId());
//            insertTransitSuppliersRouteStmt.executeUpdate();
//
//            // Insert transitId, branchStoreId, and orderDocumentId into TransitBranchStoresRoute table
//            String insertTransitBranchStoresRouteQuery = "INSERT INTO TransitBranchStoresRoute (transitId, branchStoreId, orderDocumentId) VALUES (?, ?, ?)";
//            PreparedStatement insertTransitBranchStoresRouteStmt = connection.prepareStatement(insertTransitBranchStoresRouteQuery);
//            insertTransitBranchStoresRouteStmt.setInt(1, transitId);
//            insertTransitBranchStoresRouteStmt.setInt(2, transit.getBranchStoreId());
//            insertTransitBranchStoresRouteStmt.setInt(3, transit.getOrderDocumentId());
//            insertTransitBranchStoresRouteStmt.executeUpdate();
            pendingTransitsSet.add(transit);
        } catch (SQLException e) {
            System.out.println("Error saving transit to database: " + e.getMessage());
        }
    }

//    //TODO figure out what to do with the supplier and the branch store ???
//    @Override
//    public void addOrderDocumentToTransit(Transit transit, OrderDocument orderDocument){
//        String insertOrderDocumentQuery =  "INSERT INTO TransitOrderDocuments (transitId, orderDocumentId) VALUES (?, ?)";
//        String insertSupplierToRouteQuery = "INSERT INTO TransitSuppliersRoute (transitId, orderDocumentId, supplierId) VALUES (?, ? ,?)";
//        String insertBranchStoreToRouteQuery = "INSERT INTO TransitBranchStoresRoute (transitId, orderDocumentId, branchStoreId) VALUES (?, ? ,?)";
//        int transitId = transit.getTransitId();
//        int orderDocumentId = orderDocument.getOrderDocumentId();
//        try {
//            PreparedStatement insertOrderDocumentStmt = connection.prepareStatement(insertOrderDocumentQuery);
//            insertOrderDocumentStmt.setInt(1, transitId);
//            insertOrderDocumentStmt.setInt(2, orderDocumentId);
//            insertOrderDocumentStmt.executeUpdate();
//
//            //TODO q: maybe in a separate function ??
//            PreparedStatement insertSupplierToRouteStmt = connection.prepareStatement(insertSupplierToRouteQuery);
//            insertSupplierToRouteStmt.setInt(1, transitId);
//            insertSupplierToRouteStmt.setInt(2, orderDocumentId);
//            insertSupplierToRouteStmt.setInt(3, orderDocument.getSource().getSupplierId());
//            insertSupplierToRouteStmt.executeUpdate();
//
//            //TODO q: maybe in a separate function ??
//            PreparedStatement insertBranchStoreToRouteStmt = connection.prepareStatement(insertBranchStoreToRouteQuery);
//            insertBranchStoreToRouteStmt.setInt(1, transitId);
//            insertBranchStoreToRouteStmt.setInt(2, orderDocumentId);
//            insertBranchStoreToRouteStmt.setInt(3, orderDocument.getDestination().getBranchID());
//            insertBranchStoreToRouteStmt.executeUpdate();
//
//            transit.addOrderDoc(orderDocument); //TODO update upperLevel
//            Set<Supplier> updatedSuppliersRoute = retrieveSupplierForTransit(transitId);
//            Set<BranchStore> updatedBranchStoreRoute = retrieveBranchStoresForTransit(transitId);
//            transit.setDestinationSuppliers(updatedSuppliersRoute);
//            transit.setDestinationBranchStores(updatedBranchStoreRoute);
//
//        } catch (SQLException e)
//        {
//            System.out.println("Error saving order document to transit: " + e.getMessage());
//        }
//    }
//
//    //TODO figure out what to do with the supplier and the branch store ???
//    @Override
//    public void removeOrderDocumentFromTransit(Transit transit, OrderDocument orderDocument) {
//        String deleteOrderDocumentQuery = "DELETE FROM TransitOrderDocuments WHERE transitId = ? AND orderDocumentId = ?";
//        String deleteSupplierFromRouteQuery = "DELETE FROM TransitSuppliersRoute WHERE transitId = ? AND orderDocumentId = ?";
//        String deleteBranchStoreFromRouteQuery = "DELETE FROM TransitBranchStoresRoute WHERE transitId = ? AND orderDocumentId = ?";
//        int transitId = transit.getTransitId();
//        int orderDocumentId = orderDocument.getOrderDocumentId();
//        try {
//            PreparedStatement deleteOrderDocumentStmt = connection.prepareStatement(deleteOrderDocumentQuery);
//            deleteOrderDocumentStmt.setInt(1, transitId);
//            deleteOrderDocumentStmt.setInt(2, orderDocumentId);
//            deleteOrderDocumentStmt.executeUpdate();
//
//            PreparedStatement deleteSupplierFromRouteStmt = connection.prepareStatement(deleteSupplierFromRouteQuery);
//            deleteOrderDocumentStmt.setInt(1, transitId);
//            deleteOrderDocumentStmt.setInt(2, orderDocumentId);
//            deleteSupplierFromRouteStmt.executeUpdate();
//
//            PreparedStatement deleteBranchStoreFromRouteStmt = connection.prepareStatement(deleteBranchStoreFromRouteQuery);
//            deleteOrderDocumentStmt.setInt(1, transitId);
//            deleteOrderDocumentStmt.setInt(2, orderDocumentId);
//            deleteBranchStoreFromRouteStmt.executeUpdate();
//
//            transit.removeOrderDoc(orderDocument); //TODO update upperLevel
//            Set<Supplier> updatedSuppliersRoute = retrieveSupplierForTransit(transitId);
//            Set<BranchStore> updatedBranchStoreRoute = retrieveBranchStoresForTransit(transitId);
//            transit.setDestinationSuppliers(updatedSuppliersRoute);
//            transit.setDestinationBranchStores(updatedBranchStoreRoute);
//
//        } catch (SQLException e) {
//            System.out.println("Error removing order document from transit: " + e.getMessage());
//        }
//    }


    @Override
    public void updateOrderDocumentOfTransit(Transit transit, OrderDocument orderDocument, String addOrRemoveFlag) {
        if (!addOrRemoveFlag.equals("+1") && !addOrRemoveFlag.equals("-1"))
        {
            System.out.println("Invalid addOrRemoveFlag value: " + addOrRemoveFlag + "Must be: +1 or -1");
            return;
        }
        String orderDocumentQuery;
        String supplierRouteQuery;
        String branchStoreRouteQuery;
        int transitId = transit.getTransitId();
        int orderDocumentId = orderDocument.getOrderDocumentId();

        if (addOrRemoveFlag.equals("+1"))
        {
            orderDocumentQuery =  "INSERT INTO TransitOrderDocuments (transitId, orderDocumentId) VALUES (?, ?)";
            supplierRouteQuery = "INSERT INTO TransitSuppliersRoute (transitId, orderDocumentId, supplierId) VALUES (?, ? ,?)";
            branchStoreRouteQuery = "INSERT INTO TransitBranchStoresRoute (transitId, orderDocumentId, branchStoreId) VALUES (?, ? ,?)";
            //transit.addOrderDoc(orderDocument);
        }
        else { // addOrRemoveFlag.equals("-1")
            orderDocumentQuery = "DELETE FROM TransitOrderDocuments WHERE transitId = ? AND orderDocumentId = ?";
            supplierRouteQuery = "DELETE FROM TransitSuppliersRoute WHERE transitId = ? AND orderDocumentId = ?";
            branchStoreRouteQuery = "DELETE FROM TransitBranchStoresRoute WHERE transitId = ? AND orderDocumentId = ?";
            //transit.removeOrderDoc(orderDocument);
        }

        try {
            PreparedStatement orderDocumentStmt = connection.prepareStatement(orderDocumentQuery);
            orderDocumentStmt.setInt(1, transitId);
            orderDocumentStmt.setInt(2, orderDocumentId);
            orderDocumentStmt.executeUpdate();

            PreparedStatement supplierRouteStmt = connection.prepareStatement(supplierRouteQuery);
            supplierRouteStmt.setInt(1, transitId);
            supplierRouteStmt.setInt(2, orderDocumentId);
            supplierRouteStmt.setInt(3, orderDocument.getSource().getSupplierId());
            supplierRouteStmt.executeUpdate();

            PreparedStatement branchStoreRouteStmt = connection.prepareStatement(branchStoreRouteQuery);
            branchStoreRouteStmt.setInt(1, transitId);
            branchStoreRouteStmt.setInt(2, orderDocumentId);
            branchStoreRouteStmt.setInt(3, orderDocument.getDestination().getBranchID());
            branchStoreRouteStmt.executeUpdate();


            Set<Supplier> updatedSuppliersRoute = retrieveSupplierForTransit(transitId);
            Set<BranchStore> updatedBranchStoreRoute = retrieveBranchStoresForTransit(transitId);
//            transit.setDestinationSuppliers(updatedSuppliersRoute);
//            transit.setDestinationBranchStores(updatedBranchStoreRoute);

        } catch (SQLException e)
        {
            System.out.println("Error related to order document of transit: " + e.getMessage());
        }
    }

    @Override
    public void updateTruckAndDriverOfTransit(Transit transit, Truck newTruck, Driver driver) {
        // Update the transit in the database
        String updateTransitQuery = "UPDATE Transits SET truckPlateNumber = ?, driverId = ? WHERE transitId = ?";
        try {
            PreparedStatement updateTransitStmt = connection.prepareStatement(updateTransitQuery);
            updateTransitStmt.setString(1, newTruck.getPlateNumber());
            updateTransitStmt.setInt(2,Integer.parseInt(driver.getId()));
            updateTransitStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating transit in the database: " + e.getMessage());
        }
    }
    @Override
    public Transit findTransitByID(int transitId) {
        Transit transit = lookForTransitInCache(transitId);
        if (transit != null) return transit;
        String selectTransitQuery = "SELECT * FROM Transits WHERE transitId = ?";
        try {
            PreparedStatement selectTransitStmt = connection.prepareStatement(selectTransitQuery);
            selectTransitStmt.setInt(1, transitId);
            ResultSet resultSet = selectTransitStmt.executeQuery();

            if (resultSet.next()) {
                // Retrieve the transit data from the result set
                int transitIdResult = resultSet.getInt("transitId");
                Date transitDateResult = resultSet.getDate("transitDate");
                String truckPlateNumberResult = resultSet.getString("truckId");
                int driverIdResult = resultSet.getInt("driverId");
                double etaResult = resultSet.getDouble("eta");
                Time departureTimeResult = resultSet.getTime("departureTime");
                boolean isCompletedStatusResult = resultSet.getBoolean("isCompletedStatus");
                // TODO: Retrieve the truck and driver objects based on the retrieved IDs
                Truck truck = truckDAO.findTruckByPlateNumber(truckPlateNumberResult);
                //TODO make sure this works BussinesLogic.Driver
                Driver driver = (Driver) driverDAO.findByID(driverIdResult);

                // TODO pay-attention: high coupling with ETA
                if (etaResult == 0){
                    // that means that there are not orderDocuments related to this driver
                    // Create a new Transit object with the retrieved data
                    transit = new Transit(transitIdResult, transitDateResult, truck, driver);
                }
                else {
                    Set<OrderDocument> orderDocumentSet = retrieveOrderDocumentsForTransit(transitId);
                    Set<Supplier> suppliersRoute = retrieveSupplierForTransit(transitId);
                    Set<BranchStore> branchStoresRoute = retrieveBranchStoresForTransit(transitId);
                    transit = new Transit(transitIdResult, transitDateResult, truck, driver, etaResult,suppliersRoute,
                            branchStoresRoute, orderDocumentSet);

                    if (departureTimeResult != null){
                        LocalTime departureTime = departureTimeResult.toLocalTime();
                        transit.setDepartureTime(departureTime);
                    }
                    if (isCompletedStatusResult)
                    {
                        completedTransitsSet.add(transit);
                    }
                    else {
                        pendingTransitsSet.add(transit);
                    }
                }
            }

            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error finding transit in the database: " + e.getMessage());
        }

        return transit;
    }
    private Transit lookForTransitInCache(int transitId){
        for (Transit transit : pendingTransitsSet)
        {
            if (transit.getTransitId() == transitId)
                return transit;
        }
        for (Transit transit : completedTransitsSet)
        {
            if (transit.getTransitId() == transitId)
                return transit;
        }
        return null;
    }
    private Set<OrderDocument> retrieveOrderDocumentsForTransit(int transitId) {
        Set<OrderDocument> orderDocuments = new HashSet<>();
        String selectOrderDocumentsQuery = "SELECT * FROM TransitOrderDocuments WHERE transitId = ?";

        try {
            PreparedStatement selectOrderDocumentsStmt = connection.prepareStatement(selectOrderDocumentsQuery);
            selectOrderDocumentsStmt.setInt(1, transitId);
            ResultSet resultSet = selectOrderDocumentsStmt.executeQuery();

            while (resultSet.next()) {
                // Retrieve order document data from the result set
                int orderDocumentId = resultSet.getInt("orderDocumentId");
                // TODO: Retrieve order document object based on the retrieved ID
                OrderDocument orderDocument = orderDocumentDAO.findOrderDocumentById(orderDocumentId);

                if (orderDocument != null) {
                    orderDocuments.add(orderDocument);
                }
            }

            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving order documents for transit: " + e.getMessage());
        }
        return orderDocuments;
    }
    private Set<BranchStore> retrieveBranchStoresForTransit(int transitId) {
        Set<BranchStore> branchStores = new HashSet<>();
        String selectBranchStoresRouteQuery = "SELECT * FROM TransitBranchStoresRoute WHERE transitId = ?";

        try {
            PreparedStatement selectBranchStoresRouteStmt = connection.prepareStatement(selectBranchStoresRouteQuery);
            selectBranchStoresRouteStmt.setInt(1, transitId);
            ResultSet resultSet = selectBranchStoresRouteStmt.executeQuery();

            while (resultSet.next()) {
                // Retrieve branch store data from the result set
                int branchStoreId = resultSet.getInt("branchStoreId");
                // TODO: Use the BranchStoreDAO to retrieve the BranchStore object based on the retrieved ID
                BranchStore branchStore = (BranchStore) branchStoreDAO.findByID(branchStoreId);

                if (branchStore != null) {
                    branchStores.add(branchStore);
                }
            }

            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error retrieving branch stores for transit: " + e.getMessage());
        }

        return branchStores;
    }
    private Set<Supplier> retrieveSupplierForTransit(int transitId) {
        Set<Supplier> suppliers = new HashSet<>();
        String selectSuppliersRouteQuery = "SELECT * FROM TransitSuppliersRoute WHERE transitId = ?";

        try {
            PreparedStatement selectSuppliersRouteStmt = connection.prepareStatement(selectSuppliersRouteQuery);
            selectSuppliersRouteStmt.setInt(1, transitId);
            ResultSet resultSet = selectSuppliersRouteStmt.executeQuery();

            while (resultSet.next()) {
                // Retrieve branch store data from the result set
                int supplierId = resultSet.getInt("supplierId");
                // TODO: Use the BranchStoreDAO to retrieve the BranchStore object based on the retrieved ID
                Supplier supplier = supplierDAO.findSupplierById(supplierId);

                if (supplier != null) {
                    suppliers.add(supplier);
                }
            }

            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving supplier for transit: " + e.getMessage());
        }

        return suppliers;
    }
    @Override
    public void moveToCompleted(Transit completedTransit) {
        String updateStatusQuery = "UPDATE Transits SET isCompletedStatus = true WHERE transitId = ?";
        try {
            PreparedStatement updateStatusStmt = connection.prepareStatement(updateStatusQuery);
            updateStatusStmt.setInt(1, completedTransit.getTransitId());
            updateStatusStmt.executeUpdate();
            pendingTransitsSet.remove(completedTransit);
            completedTransitsSet.add(completedTransit);
        } catch (SQLException e) {
            System.out.println("Error moving transit to completed: " + e.getMessage());
        }
    }
    @Override
    public Set<Transit> getTransitsSet(boolean isCompleted) {
        Set<Transit> transits = new HashSet<>();
        String selectPendingTransits = "SELECT * FROM Transits WHERE isCompletedStatus = false";
        String selectCompletedTransits = "SELECT * FROM Transits WHERE isCompletedStatus = true";

        try {
            PreparedStatement statement;
            if (isCompleted) {
                statement = connection.prepareStatement(selectCompletedTransits);
            } else {
                statement = connection.prepareStatement(selectPendingTransits);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int transitId = resultSet.getInt("transitId");
                Transit transit = findTransitByID(transitId);
                transits.add(transit);
            }
        } catch (SQLException e) {
            System.out.println("Error getting transits from the database: " + e.getMessage());
        }
        return transits;
    }


    @Override
    //TODO q: can delete???
    public void removeTransit(Transit transit) {
        pendingTransitsSet.remove(transit);
    }
}
