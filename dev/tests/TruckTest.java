import BussinesLogic.License;
import ControllerLayer.TruckController;
import ControllerLayer.TruckControllermpl;
import DataAccess.DAO_Generator;
import DataAccessLayer.TruckDAO;
import DomainLayer.Truck;
import DomainLayer.TruckModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class TruckTest {
    private TruckDAO truckDAO;
    private Truck truck;
    private TruckController truckController;
    @Before
    public void setUp() throws Exception {
        truckDAO = DAO_Generator.getTruckDAO();
        // Generate a random plate number for the test truck
        truckController = new TruckControllermpl(truckDAO);
        int[] iLarr = {0,2};

        //truck = truckController.createTruck("IT54432AI", 1, iLarr ,9000, 30000);
        truck = new Truck("IT54432AI", TruckModel.LARGETRUCK ,9000, 30000);
        truck.addLToLSet(License.C);
        truck.addLToLSet(License.COOLER);
    }

    @After
    public void tearDown() throws Exception {
        truckDAO.removeTruck(truck);
    }

    @Test
    public void testFindTruck() {
        truckDAO.saveTruck(truck);
        Truck savedTruck = truckDAO.findTruckByPlateNumber(truck.getPlateNumber());
        assertNotNull(savedTruck);
        assertEquals(savedTruck.getPlateNumber(), truck.getPlateNumber());
        assertEquals(savedTruck.getModel(), truck.getModel());
        assertTrue(savedTruck.getTruckLicenses().containsAll(truck.getTruckLicenses()));
        assertTrue(truck.getTruckLicenses().containsAll(savedTruck.getTruckLicenses()));
        assertEquals(savedTruck.getTruckWeight(), truck.getTruckWeight(), 0.01);
        assertEquals(savedTruck.getMaxCarryWeight(), truck.getMaxCarryWeight(), 0.01);
    }

    @Test
    public void testRemoveTruck() {
        truckDAO.saveTruck(truck);
        truckDAO.removeTruck(truck);
        Truck removedTruck = truckDAO.findTruckByPlateNumber(truck.getPlateNumber());
        assertNull(removedTruck);
    }

}
