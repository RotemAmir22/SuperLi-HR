package ControllerServiceLayer;

import DomainLayer.OrderDocument;
import DomainLayer.Transit;
import DomainLayer.Truck;
import ExceptionsPackage.UiException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class TransitController {
    private TransitService transitService;
    public TransitController(TransitService transitService) {
        this.transitService = transitService;
    }

    public void createNewTransit(Scanner scanner) {
        System.out.println("-----Create new transit-----");
        System.out.println("Enter transit Date: (dd/mm/yyyy) ");
        String sTransitDate = scanner.nextLine();
        System.out.println("Enter truck's plate number: ");
        String truckPLateNumber = scanner.nextLine();
        System.out.println("Enter driver's id: ");
        int driverId = scanner.nextInt();
        scanner.nextLine();
        Transit newTransit;
        try {
            newTransit = this.transitService.createTransit(sTransitDate, truckPLateNumber, driverId);
        } catch (UiException e) {
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
        Transit transitToReplace = findTransitById(transitId);
        if (transitToReplace == null) return;
        String truckPlateNumber = getTruckPlateHandler(scanner);
        boolean flag = transitService.setTransitTruck(transitToReplace, truckPlateNumber);
        if(!flag) {
            System.out.printf("Truck's plate number %s not found!%n", truckPlateNumber);
            return;
        }

        System.out.println("Transit's truck updated successfully");
    }

    public void printTransitById(int transitId){
        boolean flag = transitService.showTransitByID(transitId);
        if(!flag)
            System.out.printf("Transit's id: %d not found!%n", transitId);
    }

    public int getTransitIdHandler(Scanner scanner)
    {
        System.out.println("Enter transit id: ");
        int transitId = scanner.nextInt();
        return transitId;
    }

    public String getTruckPlateHandler(Scanner scanner)
    {
        System.out.println("Enter truck plate number: ");
        String truckPlateNumber = scanner.nextLine();
        return truckPlateNumber;
    }
    public void beginTransit(Scanner scanner){
        int transitId = getTransitIdHandler(scanner);
        Transit transit = findTransitById(transitId);
        //check if date is today
        if (!transit.getTransitDate().equals(LocalDate.now()))
        {
            System.out.println("Warning, the date of transaction is not today ");
            System.out.println("Back to main menu ");
            return;
        }
        transit.setDepartureTime(LocalTime.now());
        // load?
        double weight = 0;
        Truck truck = transit.getTruck();
        for (OrderDocument orderDocument: transit.getOrdersDocs())
        {
            weight += orderDocument.getTotalWeight();
            truck.loadTruck(weight); // truck service ?
            //if overweight - need to add to TransitDocument that there was a problem
            if (truck.getMaxCarryWeight()<truck.getCurrentWeight()) {overWeight(scanner);}
            /**
             * all good, can go unload in store
             *  or do i need to continue to all destinations and only
             * then go back to stores?
            **/
            transit.getTruck().unloadTruck(weight);
            //create in repository a set of orders that are finished and add the current order there
            //
        }
        //create in repository a set of transits that are finished and add this transit there
    }
    public void overWeight(Scanner scanner)
    {
        int ans;
        //scanner.nextLine();
        System.out.println("Overweighted truck - what would you wish to do? ");
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
