package DataAccessLayer;

import BussinesLogic.BranchStore;
import DataAccess.DAO_BranchStore;
import DataAccess.Database;
import DomainLayer.OrderDocument;
import DomainLayer.Product;
import DomainLayer.Supplier;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OrderDocumentDAOImpl implements OrderDocumentDAO {
    private final Set<OrderDocument> pendingOrdersDocumentsSet = new HashSet<>();
    private final Set<OrderDocument> completedOrdersDocumentsSet = new HashSet<>();
    private final Connection connection;
    private final SupplierDAO supplierDAO; // TODO when merge with suppliers module see also branch store
    private final ProductDAO productDAO;
    private final DAO_BranchStore branchStoreDAO; //TODO check if can create here instead of passing as parameter

    public OrderDocumentDAOImpl(SupplierDAO supplierDAO, DAO_BranchStore branchStoreDAO, ProductDAO productDAO) throws SQLException, ClassNotFoundException {
        connection = Database.connect();
        this.supplierDAO = supplierDAO;
        this.branchStoreDAO = branchStoreDAO;
        this.productDAO = productDAO;
    }

    @Override
    public void saveOrderDocument(OrderDocument orderDocument) {
        String insertOrderDocumentSQL = "INSERT INTO OrderDocuments(orderDocumentId, sourceSupplierId, destinationBranchStoreId, totalWeight) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement insertOrderDocumentStatement = connection.prepareStatement(insertOrderDocumentSQL, Statement.RETURN_GENERATED_KEYS);
            insertOrderDocumentStatement.setInt(1, orderDocument.getOrderDocumentId());
            insertOrderDocumentStatement.setInt(2, orderDocument.getSource().getSupplierId());
            insertOrderDocumentStatement.setInt(3, orderDocument.getDestination().getBranchID());
            insertOrderDocumentStatement.setDouble(4, orderDocument.getTotalWeight());
            insertOrderDocumentStatement.executeUpdate();

            ResultSet generatedKeys = insertOrderDocumentStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);
                String insertOrderDocumentProductSQL = "INSERT INTO OrderDocumentProducts (orderDocumentId, productId, productAmount) VALUES (?, ?, ?)";
                PreparedStatement insertOrderDocumentProductStatement = connection.prepareStatement(insertOrderDocumentProductSQL);
                for (Map.Entry<Product, Double> entry : orderDocument.getProductsList().entrySet()) {
                    insertOrderDocumentProductStatement.setInt(1, orderId);
                    insertOrderDocumentProductStatement.setInt(2, entry.getKey().getProductId());
                    insertOrderDocumentProductStatement.setDouble(3, entry.getValue());
                    insertOrderDocumentProductStatement.executeUpdate();
                }
            }
            pendingOrdersDocumentsSet.add(orderDocument);
        } catch (SQLException e) {
            System.out.println("Error saving order document to database: " + e.getMessage());
        }
    }

    @Override
    public OrderDocument findOrderDocumentById(int orderDocId) {
        // Check if the order document is in the pendingOrdersDocumentsSet
        for (OrderDocument pendingDoc : pendingOrdersDocumentsSet) {
            if (pendingDoc.getOrderDocumentId() == orderDocId) {
                return pendingDoc;
            }
        }

        for (OrderDocument completedDoc : completedOrdersDocumentsSet) {
            if (completedDoc.getOrderDocumentId() == orderDocId) {
                return completedDoc;
            }
        }

        // If the order document isn't in the pendingOrdersDocumentsSet nor in completedOrdersDocumentsSet, query the database
        String query = "SELECT * FROM OrderDocuments WHERE orderDocumentId = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderDocId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int sourceSupplierId = resultSet.getInt("sourceSupplierId");
                Supplier sourceSupplier = supplierDAO.findSupplierById(sourceSupplierId); // Assuming you have a method for finding a Supplier by ID
                int destinationBranchStoreId = resultSet.getInt("destinationBranchStoreId");

                BranchStore destinationStore = (BranchStore) branchStoreDAO.findByID(destinationBranchStoreId); // Assuming you have a method for finding a Store by ID
                double totalWeight = resultSet.getDouble("totalWeight");


                Map<Product, Double> productsList = new HashMap<>();
                String selectOrderDocumentProductsSQL = "SELECT * FROM OrderDocumentProducts WHERE orderDocumentId = ?";
                try {
                    PreparedStatement selectOrderDocumentProductsStatement = connection.prepareStatement(selectOrderDocumentProductsSQL);
                    selectOrderDocumentProductsStatement.setInt(1, orderDocId);
                    ResultSet orderDocumentProductsResult = selectOrderDocumentProductsStatement.executeQuery();
                    while (orderDocumentProductsResult.next()) {
                        int productId = orderDocumentProductsResult.getInt("productId");
                        double productAmount = orderDocumentProductsResult.getDouble("productAmount");
                        Product product = productDAO.findProductById(productId);
                        productsList.put(product, productAmount);
                    }
                } catch (SQLException e) {
                    System.out.println("Error retrieving order document products from database: " + e.getMessage());
                }

                // TODO think about problem that can occur by this constructor and by the orderDocumentId
                OrderDocument orderDocument = new OrderDocument(orderDocId, sourceSupplier, destinationStore, totalWeight, productsList);
                return orderDocument;
            }
        } catch (SQLException e) {
            System.out.println("Error finding order document by ID: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null; // If the order document isn't found in the pendingOrdersDocumentsSet or the database, return null
    }

    @Override
    public void moveToCompleted(OrderDocument completedOrder) {
        String updateOrderDocSQL = "UPDATE OrderDocuments SET isCompletedStatus = true WHERE orderDocumentId = ?";
        try {
            // Update the OrderDocuments table to set the status to "completed"
            PreparedStatement updateOrderDocStatement = connection.prepareStatement(updateOrderDocSQL);
            updateOrderDocStatement.setInt(1, completedOrder.getOrderDocumentId());
            updateOrderDocStatement.executeUpdate();
            // Remove the order document from the pendingOrdersDocumentsSet in memory
            pendingOrdersDocumentsSet.remove(completedOrder);
            // Add the order document to the completedOrdersDocumentsSet in memory
            completedOrdersDocumentsSet.add(completedOrder);
        } catch (SQLException e) {
            System.out.println("Error moving order document to completed: " + e.getMessage());
        }
    }

    @Override
    public Set<OrderDocument> getOrderDocsSet(boolean isCompleted) {
        Set<OrderDocument> orderDocs = new HashSet<>();
        String selectPendingOrderDocs = "SELECT * FROM OrderDocuments WHERE isCompletedStatus = false";
        String selectCompletedOrderDocs = "SELECT * FROM OrderDocuments WHERE isCompletedStatus = true";

        try {
            PreparedStatement statement;
            if (isCompleted){
                statement = connection.prepareStatement(selectCompletedOrderDocs);
            }
            else {
                statement = connection.prepareStatement(selectPendingOrderDocs);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int orderDocId = resultSet.getInt("orderDocumentId");
                OrderDocument orderDoc = findOrderDocumentById(orderDocId);
                orderDocs.add(orderDoc);
            }
        } catch (SQLException e) {
            System.out.println("Error getting order documents from database: " + e.getMessage());
        }
        return orderDocs;
    }

    @Override
    public void updateOrderDocumentWeight(int orderDocumentId, double weight) {
        String updateWeightQuery = "UPDATE OrderDocuments SET totalWeight = ? WHERE orderDocumentId = ?";
        try {
            PreparedStatement updateWeightStmt = connection.prepareStatement(updateWeightQuery);
            updateWeightStmt.setDouble(1, weight);
            updateWeightStmt.setInt(2, orderDocumentId);
            updateWeightStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating weight in the database: " + e.getMessage());
        }
    }

    @Override
    public void updateProductAmount(int orderDocumentId, int productId, double weight) {

        String updateProductAmountQuery = "UPDATE OrderDocumentProducts SET productAmount = ? WHERE orderDocumentId = ? AND productId = ?";
        try {
            PreparedStatement updateProductAmountStmt = connection.prepareStatement(updateProductAmountQuery);
            updateProductAmountStmt.setDouble(1, weight);
            updateProductAmountStmt.setInt(2, orderDocumentId);
            updateProductAmountStmt.setInt(3, productId); // Assuming you have a function to find the product ID by name

            updateProductAmountStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating product amount in the database: " + e.getMessage());
        }
    }

}
