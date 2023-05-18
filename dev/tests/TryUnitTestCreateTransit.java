//import BussinesLogic.TransitCoordinator;
//import ControllerLayer.*;
//import DomainLayer.Transit;
//import ExceptionsPackage.UiException;
//import org.junit.*;
//public class TryUnitTestCreateTransit {
//    private TruckController truckController;
//    private ProductController productController;
//    private SupplierController supplierController;
//    private OrderDocumentController orderDocumentController;
//    private TransitController transitController;
//    private TransitRecordController transitRecordController;
//    private Transit transit;
//
//
//    @Before
//    public void setUp() throws Exception {
//        truckController = ControllerGen.getTruckController();
//        productController =  ControllerGen.getProductController();
//        supplierController = ControllerGen.getSupplierController();
//        orderDocumentController = ControllerGen.getOrderDocumentController();
//        transitRecordController = ControllerGen.getTransitRecordController();
//        transitController = ControllerGen.getTransitController();
//    }
//
////    @After
////    public void tearDown() throws Exception {
////        transitController.removeTransitCompletely();
////    }
//
//    @Test
//    public void testCreateTransit() throws UiException {
//        transit = transitController.createTransit("18-05-2023", "123", "888888");
//
//    }
//
//    @Test
//    public void testRemoveTransitCompletely()
//    {
//        transitController.removeTransitCompletely(transit);
//    }
//
//}
