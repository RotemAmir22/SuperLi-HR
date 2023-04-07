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
        System.out.println("Transit added successfully!");
        //TODO also print the truck info ?
    }

}
