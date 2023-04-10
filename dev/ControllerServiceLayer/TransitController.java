package ControllerServiceLayer;
import DomainLayer.*;
import ExceptionsPackage.ModuleException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

public class TransitController {
    private final TransitService transitService;
    public TransitController(TransitService transitService) {
        this.transitService = transitService;
    }

    public void showAllTransits()
    {

    }
    public void createNewTransit(Scanner scanner) {
        System.out.println("-----Create new transit-----");
        System.out.println("Enter transit Date: (dd-mm-yyyy) ");
        String sTransitDate = scanner.nextLine();
        System.out.println("Enter truck's plate number: ");
        String truckPLateNumber = scanner.nextLine();
        System.out.println("Enter driver's id: ");
        int driverId = scanner.nextInt();
        scanner.nextLine();
        Transit newTransit;
        try {
            newTransit = this.transitService.createTransit(sTransitDate, truckPLateNumber, driverId);
        } catch (ModuleException e) {
            System.out.println(e.getMessage());
            return;
        }
        // TODO figure out the correct way of doing this v.
        this.transitService.getTransitRepo().saveTransit(newTransit);
        newTransit.printTransit();
        System.out.println("Transit added successfully!");

    }

    public Transit findTransitById(int transitId){
        Transit transitFound = transitService.findTransitByID(transitId);
        if (transitFound!=null)return transitFound;
        System.out.printf("Transit id: %d not found %n",transitId);
        return null;
    }

    public void printTransitDetails(Scanner scanner){
        int transitId = getTransitIdHandler(scanner);
        printTransitById(transitId);
    }


    public void replaceTransitTruck(Scanner scanner){
        int transitId = getTransitIdHandler(scanner);
        String newTruckPlate = getTruckPlateHandler(scanner);
        int flag = transitService.replaceTransitTruck(transitId, newTruckPlate);
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

//    public void replaceTransitTruck(Scanner scanner){
//        int transitId = getTransitIdHandler(scanner);
//        String newTruckPlate = getTruckPlateHandler(scanner);
//        Transit currentTransit = findTransitById(transitId);
//        Truck newTruck = findTruckByPlate(newTruckPlate);
//
//        if (currentTransit == null || newTruck == null )return;
//        boolean confirmNewTruck = isOkReplaceTruck(currentTransit, newTruck);
//        while (!confirmNewTruck){ //the driver is not allowed to drive the truck
//
//        }
//        Driver potencialDriver = findDriverById(int driverID);
//
//        if (currentTransit == null) eturn;
//        int flag = transitService.replaceTransitTruck(transitId, newTruckPlate);
//        switch (flag) {
//            case 0:
//                System.out.println("Current driver is not qualified to drive the chosen truck");
//                lookForQualifiedDriver(scanner, transitId, newTruckPlate);
//                return;
//            case 1:
//                System.out.println("Transit's truck updated successfully");
//                return;
//
//        }
//    }


    public void printTransitById(int transitId){
        boolean flag = transitService.showTransitByID(transitId);
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

    public void lookForQualifiedDriver(Scanner scanner, int transitIdToReplace, String newTruckPlate)
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
                int driverId = scanner.nextInt();
                scanner.nextLine();
                iFlag = this.transitService.replaceTransitDriver(transitIdToReplace, driverId, newTruckPlate);
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
        boolean validWeight = transitService.isValidWeight(currentTransit, orderDocument);
        if (!validWeight) return;
        currentTransit.addOrderDoc(orderDocument);
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
        System.out.println("Document removed successfully");
    }

    public int getOrderIdHandler(Scanner scanner){
        System.out.println("Enter order id: ");
        int orderId = scanner.nextInt();
        return orderId;
    }
    public OrderDocument findOrderById(int orderId){
        OrderDocument orderDocFound = transitService.getOrderDocService().findOrderDocById(orderId);
        if (orderDocFound == null)
            System.out.printf("Order Document id: %d not found %n",orderId);
        return orderDocFound;
    }

public void beginTransit (Scanner scanner)
{
    boolean overload = false;
    int transitId = getTransitIdHandler(scanner);
    Transit transit = findTransitById(transitId);
    TransitRecord transitRecord = this.transitService.getTransitRecordService().createTransitRecord(transit);
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
    Truck truck = transit.getTruck();
    //add destinations
    for(OrderDocument orderDoc : transit.getOrdersDocs())
    {
        transit.addDestinationSupplier(orderDoc.getSource());
    }
    // drive to suppliers
    for (Supplier supplier : transit.getDestinationSuppliers())
    {
        System.out.println("Arrived to supplier: " + supplier.getSupplierID());
        //check all orders that are in the suppliers source
        for(OrderDocument orderDoc : transit.getOrdersDocs())
        {
            if(supplier.getSupplierID() == orderDoc.getSource().getSupplierID())
            {

                System.out.println("Loading order number: " + orderDoc.getDocumentId());
                truck.loadTruck(orderDoc.getTotalWeight());
                if (truck.getMaxCarryWeight()<truck.getCurrentWeight())
                {
//                    overload = true;
                    transitRecord.setTransitProblem(true);
                    //overWeight();
                }
            }
        }
        transitRecord.addSupWeightExit(supplier,truck.getCurrentWeight());
    }
    //add destinations
    for(OrderDocument orderDoc : transit.getOrdersDocs())
    {
        transit.addDestinationStore(orderDoc.getDestination());
    }
    for (Store store : transit.getDestinationStores()) {
        System.out.println("Arrived to store: " + store.getStoreId());
        //check all orders that are in the stores destination
        for (OrderDocument orderDoc : transit.getOrdersDocs()) {
            if (store.getStoreId() == orderDoc.getDestination().getStoreId()) {

                System.out.println("Unloading order number: " + orderDoc.getDocumentId());
                truck.unloadTruck(orderDoc.getTotalWeight());
                this.transitService.getOrderDocService().moveOrderToFinished(orderDoc);
            }
        }
    }
    this.transitService.getTransitRecordService().getTransitRecordRepo().saveTransitRecord(transitRecord);
    System.out.println("finished transit ");
    this.transitService.moveTransitToFinished(transit);
}

    public void printAllTransitRecords(){
        this.transitService.getTransitRecordService().showTransitRecords();
    }

    public void overWeight(Scanner scanner)
    {
        int ans;
        //scanner.nextLine();
        System.out.println("Overweight truck - what would you wish to do? ");
        System.out.println("1. switch truck ");
        System.out.println("2. delete order from transit ");
        System.out.println("3. remove products from order ");
        ans = scanner.nextInt();
        switch (ans)
        {
            case 1:// switch truck

                break;
            case 2: // delete order
                break;
            case 3: //delete products from order
                break;
            default:
                System.out.println("The defult choice is to delete order from transit");
                //case 2
        }
    }
}
