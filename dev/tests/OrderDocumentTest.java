import BussinesLogic.Area;
import BussinesLogic.BranchStore;
import DataAccess.DAO_BranchStore;
import DataAccess.DAO_Generator;
import DataAccessLayer.*;
import DomainLayer.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class OrderDocumentTest {
    private SupplierDAO supplierDAO;
    private DAO_BranchStore daoBranchStore;
    private OrderDocumentDAO orderDocumentDAO;
    private OrderDocument od1;
    private OrderDocument od2;
    @Before
    public void setUp() throws Exception {
        supplierDAO = DAO_Generator.getSupplierDAO();
        daoBranchStore = DAO_Generator.getBranchStoreDAO();
        orderDocumentDAO = DAO_Generator.getOrderDocumentDAO();
        // Generate a random plate number for the test truck
        BranchStore b1 = (BranchStore) daoBranchStore.findByID(1);
        Supplier s1 = supplierDAO.findSupplierById(1);
        od1 = new OrderDocument(s1, b1);

       // BranchStore b2 = new BranchStore("a", Area.East,"aa","0", "1-1-1");
        //od2 = new OrderDocument(s1, b2);
    }

//    @After
//    public void tearDown() throws Exception {
//
//    }

    @Test
    public void testSaveOrderDoc() {

        orderDocumentDAO.saveOrderDocument(od1);
        OrderDocument Sod = orderDocumentDAO.findOrderDocumentById(od1.getOrderDocumentId());
        assertNotNull(Sod);
        assertEquals(od1.getSource().getSupplierId(), Sod.getSource().getSupplierId());
        assertEquals(od1.getDestination().getBranchID(), Sod.getDestination().getBranchID());
        double delta = 0.001;
        assertEquals(od1.getTotalWeight(), Sod.getTotalWeight(), delta);
    }

//    @Test
//    public void testSaveOrderDoc2() {
//        orderDocumentDAO.saveOrderDocument(od2);
//        OrderDocument Sod = orderDocumentDAO.findOrderDocumentById(od2.getOrderDocumentId());
//        assertNotNull(Sod);
//        assertEquals(od2.getSource().getSupplierId(), Sod.getSource().getSupplierId());
//        assertEquals(od2.getDestination().getBranchID(), Sod.getDestination().getBranchID());
//        assertEquals(od2.getTotalWeight(), Sod.getTotalWeight());
//    }



}
