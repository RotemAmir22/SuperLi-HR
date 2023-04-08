package ControllerServiceLayer;

import DomainLayer.Transit;
import ExceptionsPackage.UiException;

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

}
