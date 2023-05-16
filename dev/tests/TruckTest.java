import DataAccessLayer.TruckDAOImpl;
import DomainLayer.Truck;
import DomainLayer.TruckModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TruckTest {
    private TruckDAOImpl truckDAO;
    private Truck truck;

    @Before
    public void setUp() throws Exception {
        truckDAO = new TruckDAOImpl();
        // Generate a random plate number for the test truck
        truck = new Truck("IT54432AI", TruckModel.LARGETRUCK, 2000.0, 15000.0);
    }

    @After
    public void tearDown() throws Exception {
        truckDAO.removeTruck(truck);
    }

    @Test
    public void testSaveTruck() {
        truckDAO.saveTruck(truck);
        Truck savedTruck = truckDAO.findTruckByPlateNumber(truck.getPlateNumber());
        assertNotNull(savedTruck);
        assertEquals(truck.getPlateNumber(), savedTruck.getPlateNumber());
        assertEquals(truck.getModel(), savedTruck.getModel());
        assertEquals(truck.getTruckWeight(), savedTruck.getTruckWeight(), 0.01);
        assertEquals(truck.getMaxCarryWeight(), savedTruck.getMaxCarryWeight(), 0.01);
    }

    @Test
    public void testRemoveTruck() {
        truckDAO.saveTruck(truck);
        truckDAO.removeTruck(truck);
        Truck removedTruck = truckDAO.findTruckByPlateNumber(truck.getPlateNumber());
        assertNull(removedTruck);
    }

}
