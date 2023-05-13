package UiLayer;
import BussinesLogic.BranchStore;
import BussinesLogic.Driver;
import BussinesLogic.License;
import BussinesLogic.TransitCoordinator;
import ControllerLayer.TransitController;
import ControllerLayer.TruckController;
import DomainLayer.*;
import ExceptionsPackage.UiException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

public class TransitPresentation {
    private final TransitController transitController;
    private final TruckController truckController;
    private final TransitCoordinator transitCoordinator;
    public TransitPresentation(TransitController transitController, TruckController truckController,
                               TransitCoordinator transitCoordinator) {
        this.transitController = transitController;
        this.truckController = truckController;
        this.transitCoordinator = transitCoordinator;
    }


    public void createNewTransit(Scanner scanner) {
        System.out.println("-----Create new transit-----");
        System.out.println("Enter transit Date: (dd-mm-yyyy) ");
        String sTransitDate = scanner.nextLine();
        System.out.println("Enter truck's plate number: ");
        String truckPLateNumber = scanner.nextLine();
        System.out.println("Enter driver's id: ");
        String driverId = scanner.nextLine();
//        int driverId = scanner.nextInt();
//        scanner.nextLine();
        Transit newTransit;
        try {
            newTransit = this.transitController.createTransit(sTransitDate, truckPLateNumber, driverId);
        } catch (UiException e) {
            System.out.println(e.getMessage());
            return;
        }
        // TODO figure out the correct way of doing this v.
        this.transitController.getTransitDAO().saveTransit(newTransit);
        newTransit.printTransit();
        System.out.println("Transit added successfully!");

    }
    public Transit findTransitById(int transitId){
        Transit transitFound = transitController.findTransitByID(transitId);
        if (transitFound!=null)return transitFound;
        System.out.printf("Transit id: %d not found %n",transitId);
        return null;
    }
    public void printTransitDetails(Scanner scanner){
        int transitId = getTransitIdHandler(scanner);
        printTransitById(transitId);
    }
    /**
     * replaceTruck (option 5) not on the fly
     * @param scanner scanner
     */

    public void replaceTransitTruck(Scanner scanner){
        int transitId = getTransitIdHandler(scanner);
        String newTruckPlate = getTruckPlateHandler(scanner);
        int flag = transitController.replaceTransitTruck(transitId, newTruckPlate);
        switch (flag) {
            case -2:
                System.out.printf("Transit id %d not found! %n", transitId);
                return;
            case -1:
                System.out.printf("Truck's plate number %s not found!%n", newTruckPlate);
                return;
            case 0:
                System.out.println("Current driver is not qualified to drive the chosen truck");
                lookForQualifiedDriver(scanner, transitId, newTruckPlate);
                return;
            case 1:
                System.out.println("Transit's truck updated successfully");
                return;
        }
    }
    public void printTransitById(int transitId){
        boolean flag = transitController.showTransitByID(transitId);
        if(!flag)
            System.out.printf("Transit's id: %d not found!%n", transitId);
    }
    public int getTransitIdHandler(Scanner scanner)
    {
        System.out.println("Enter transit id: ");
        int transitId = scanner.nextInt();
        if (scanner.hasNextLine())scanner.nextLine();
        return transitId;
    }
    public String getTruckPlateHandler(Scanner scanner)
    {
        System.out.println("Enter truck plate number: ");
        String truckPlateNumber = scanner.nextLine();
        return truckPlateNumber;
    }


    private void lookForQualifiedDriver(Scanner scanner, int transitIdToReplace, String newTruckPlate)
    {
        int choice, iFlag=1;
        do {
            System.out.println("How would you like to continue: ");
            System.out.println("1. Cancel truck replacement ");
            System.out.println("2. Change Driver ");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice==1)break;
            else if (choice==2) {
                System.out.println("Enter driver id: ");
                String driverId = scanner.nextLine();
                iFlag = this.transitController.replaceTransitDriver(transitIdToReplace, driverId, newTruckPlate);
                if (iFlag==-1){
                    System.out.printf("Driver id: %d not found! %n", driverId);
                } else if (iFlag==0) {
                    System.out.println("Current driver is not qualified to drive the chosen truck");
                }
                else if (iFlag == 1){
                    System.out.println("Transit's driver updated successfully");
                }
            }
        }while (choice != 2 || iFlag != 1);
    }
    public void addOrderToTransit(Scanner scanner)
    {
        int transitId = getTransitIdHandler(scanner);
        Transit currentTransit = findTransitById(transitId);
        if (currentTransit == null) return;
        int orderId = getOrderIdHandler(scanner);
        OrderDocument orderDocument = findOrderById(orderId);
        if (orderDocument == null) return;
//        this is for checking weight before we start the transit.
//        boolean validWeight = transitService.isValidWeight(currentTransit, orderDocument);
//        if (!validWeight) return;
        currentTransit.addOrderDoc(orderDocument);
        currentTransit.addDestinationStore(orderDocument.getDestination());
        currentTransit.addDestinationSupplier(orderDocument.getSource());
        currentTransit.setETA();
        System.out.println("Order document added successfully");
    }
    public void removeOrderFromTransit(Scanner scanner)
    {
        int transitId = getTransitIdHandler(scanner);
        Transit currenTransit = findTransitById(transitId);
        if (currenTransit == null) return;
        int orderId = getOrderIdHandler(scanner);
        OrderDocument orderDocument = findOrderById(orderId);
        if (orderDocument == null) return;
        currenTransit.removeOrderDoc(orderDocument);

        currenTransit.removeDestinationSupplier(orderDocument.getSource());
        currenTransit.removeDestinationStore(orderDocument.getDestination());
        currenTransit.setETA();
        System.out.println("Document removed successfully");
    }
    public int getOrderIdHandler(Scanner scanner){
        System.out.println("Enter order id: ");
        int orderId = scanner.nextInt();
        return orderId;
    }
    public String getProductNameHandler(Scanner scanner){
        System.out.println("Enter product name: ");
        String productName = scanner.nextLine();
        return productName;
    }
    public OrderDocument findOrderById(int orderId){
        OrderDocument orderDocFound = transitController.getOrderDocController().findOrderDocById(orderId);
        if (orderDocFound == null)
            System.out.printf("Order Document id: %d not found %n",orderId);
        return orderDocFound;
    }
    public void beginTransit(Scanner scanner) {
        boolean overload = false;

        int transitId = getTransitIdHandler(scanner);
        Transit transit = findTransitById(transitId);
        if (transit == null) return;
        //check if date is today

        LocalDateTime presentDate = LocalDate.now().atStartOfDay(); // using java.time.LocalDate
        Date presentDateAlt = Date.from(presentDate.atZone(ZoneId.systemDefault()).toInstant()); // using java.util.Date

        if (transit.getTransitDate().compareTo(presentDateAlt) != 0)
        {
            System.out.println("Warning, the date of transaction is not today ");
            System.out.println("Back to main menu ");
            return;
        }
        transit.setDepartureTime(LocalTime.now());
        System.out.println("ETA in minutes for the transit: " + transit.getETA());

        TransitRecord transitRecord = this.transitController.getTransitRecordController().createTransitRecord(transit);

        for (Supplier supplier : transit.getDestinationSuppliers())
        {
            System.out.println("Arrived to supplier: " + supplier.getSupplierId());
            //check all orders that are in the suppliers source
            for(OrderDocument orderDoc : transit.getOrdersDocs())
            {
                if(supplier.getSupplierId() == orderDoc.getSource().getSupplierId())
                {

                    System.out.println("Loading order number: " + orderDoc.getDocumentId());
                    transit.getTruck().loadTruck(orderDoc.getTotalWeight());
                    if (transit.getTruck().getMaxCarryWeight()<transit.getTruck().getCurrentWeight())
                    {
                        transitRecord.updateTransitProblem();
                        overweight(scanner,transit,orderDoc);
                    }
                }
            }
            transitRecord.addSupWeightExit(supplier,transit.getTruck().getCurrentWeight());
        }

        for (BranchStore branchStore : transit.getDestinationStores()) {
            System.out.println("Arrived to branchStore: " + branchStore.getBranchID());
            //check all orders that are in the stores destination
            for (OrderDocument orderDoc : transit.getOrdersDocs()) {
                if (branchStore.getBranchID() == orderDoc.getDestination().getBranchID()) {

                    System.out.println("Unloading order number: " + orderDoc.getDocumentId());
                    transit.getTruck().unloadTruck(orderDoc.getTotalWeight());
                    this.transitController.getOrderDocController().moveOrderToFinish(orderDoc);
                }
            }
        }
        this.transitController.getTransitRecordController().getTransitRecordDAO().saveTransitRecord(transitRecord);
        System.out.println("finished transit ");
        this.transitController.moveTransitToFinished(transit);
    }
    public void printAllTransitRecords(){
        this.transitController.getTransitRecordController().showTransitRecords();
    }
    public void overweight(Scanner scanner, Transit transit, OrderDocument currentOrder) {
        int choice;
        boolean verifiedFlag = false;
        do{
            System.out.println("-----Overweight-----");
            System.out.println("Enter your choice: ");
            System.out.println("1. Bring bigger truck ");
            System.out.println("2. Remove products from this order ");
            System.out.println("3. Delete this order from transit ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:// switch truck
                    verifiedFlag = bringBiggerTruck(scanner,transit);
                    break;
                case 2: //delete products from order
                    if (currentOrder.getProductsList().size() > 1){
                        verifiedFlag = removeProductFromOrder(scanner, transit, currentOrder);
                        break;
                    }
                    else {
                        currentOrder.printOrder();
                        System.out.println("Order id: " + currentOrder.getDocumentId() + " contains only one product, removing order ");
                    }
                case 3:// delete order
                    // update weight
                    double updatedLoadWeight =  transit.getTruck().getCurrentWeight() - currentOrder.getTotalWeight();
                    transit.getTruck().setCurrentLoadWeight(updatedLoadWeight);
                    transit.getOrdersDocs().remove(currentOrder);
                    // TODO if need to remove
                    verifiedFlag = true;
                    break;
                default:
                    System.out.println("Invalid input");
            }
        } while (!verifiedFlag);
    }
    private boolean bringBiggerTruck(Scanner scanner, Transit transit) {
        // TODO verify truck and driver availability
        Truck biggerTruck = findNewTruck(scanner);
        if (biggerTruck == null)return false;
        Driver newDriver = findNewDriver(scanner,transit.getTransitDate(),biggerTruck.getTruckLicenses(),transit.getDriver().getId());
        if (newDriver == null)return false;

        if (!transitController.isDriverAllowToDriveTruck(biggerTruck, newDriver)){
            System.out.printf("Chosen driver: %s is not qualified to drive the chosen truck %n", newDriver.getId());
            return false;
        }
        Truck smallTuck = transit.getTruck();
        if (transitController.transferLoad(smallTuck, biggerTruck))
        {
            transit.setDriver(newDriver);
            transit.setTruck(biggerTruck);
            return true;
        }
        return false;
    }
    private boolean removeProductFromOrder(Scanner scanner, Transit transit, OrderDocument orderDocument){
        double truckCurrentWeight = transit.getTruck().getCurrentWeight();
        double overWeightAmount = truckCurrentWeight - transit.getTruck().getMaxCarryWeight();
        double originalOrderWeight = orderDocument.getTotalWeight();
        System.out.println("Reduce at least: " + overWeightAmount + " kg");
        orderDocument.printOrder();
        String sProductName = getProductNameHandler(scanner);
        transitController.getOrderDocController().removeProduct(transit.getTransitId(), sProductName);
        double updatedOrderWeight = orderDocument.getTotalWeight();
        double newCurrentWeight = truckCurrentWeight - (originalOrderWeight - updatedOrderWeight);
        transit.getTruck().setCurrentLoadWeight(newCurrentWeight);
        return !(newCurrentWeight > transit.getTruck().getMaxCarryWeight());
    }
    public Truck findNewTruck(Scanner scanner) {
        System.out.println("Enter truck's plate number: ");
        String sPlateNumber = scanner.nextLine();
        Truck newTruck = truckController.findTruckByPlate(sPlateNumber);
        if (newTruck == null){
            System.out.printf("Truck's plate number: %s not found %n", sPlateNumber);
        }
        return newTruck;
    }
    public Driver findNewDriver(Scanner scanner, Date transitDate, Set<License> licenses, String oldDriver ) {
        System.out.println("Enter driver's id: ");
        String driverId = scanner.nextLine();
        Driver newDriver = transitCoordinator.SwitchDriverInTransit(transitDate,driverId,licenses,oldDriver);
        if (newDriver == null){
            System.out.printf("Driver's id: %s not found %n", driverId);
        }
        return newDriver;
    }

}
