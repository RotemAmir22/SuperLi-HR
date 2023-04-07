package ControllerServiceLayer;

import DomainLayer.Qualification;
import DomainLayer.Truck;
import DomainLayer.TruckModel;
import ExceptionsPackage.UiException;

import java.util.Scanner;

public class TruckController {
    private final TruckService truckService;

    public TruckController(TruckService truckService) { this.truckService = truckService;
    }

    /**
     * This function interacts with the user to collect information about a new truck:
     * plate number, weight, max carry weight, and special qualifications.
     * Once all the required information is gathered, it uses the ControllerServiceLayer.TruckService and DataRepository
     * interfaces to create and save a new truck object to a database.
     */
    public void createNewTruck(Scanner scanner){
        String plateNumber;
        double truckWeight;
        double maxWeight;
        int iModel;
        System.out.println("-----Add new truck-----");
        System.out.println("Enter truck plate number: "); //assuming valid plate number
        scanner.nextLine();
        plateNumber = scanner.nextLine();
        try {
            iModel = truckModelHandler(scanner); // Handler for getting truck model information
        } catch (UiException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Enter truck weight: (kg) "); //assuming valid truck weight
        truckWeight = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter truck max carry weight: (kg) "); //assuming valid max carry weight
        maxWeight = scanner.nextDouble();
        scanner.nextLine();

        String ans;
        int[] iQArr = new int[0];
        while (true){
            System.out.println("Does this truck have special qualifications? (Y/N)");
            ans = scanner.nextLine().toUpperCase();
            switch (ans.charAt(0)) {
                case 'Y':
                    try {
                        iQArr = truckQualificationsHandler(scanner); // Handler for getting truck qualifications
                    } catch (UiException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                    break;
                case 'N':
                    System.out.println("No special qualifications");
                    break;
                default:
                    System.out.println("Invalid input.");
                    continue;
            }
            break;
        }
        Truck newTruck = this.truckService.createTruck(plateNumber, iModel, iQArr, truckWeight, maxWeight);
        // TODO figure out the correct way of doing this v.
        this.truckService.getTruckRepo().saveTruck(newTruck);
        System.out.println("Truck added successfully!");
        //TODO also print the truck info ?
    }

    public void removeTruckByPlate(Scanner scanner){
        String tPlateNumber;
        System.out.println("-----Remove truck-----");
        System.out.println("Enter truck's plate number: ");
        scanner.nextLine();
        tPlateNumber = scanner.nextLine();
        boolean flag = this.truckService.removeTruckByPlateNumber(tPlateNumber);
        if (flag) System.out.printf("DomainLayer.Truck with plate number: %s deleted successfully!%n", tPlateNumber);
        else {
            System.out.println("There is no truck with given plate number: " + tPlateNumber);
        }
    }

    void printTruckByPlate(String tPLateNumber){
        boolean flag = this.truckService.showTruckByPlate(tPLateNumber);
        if (!flag){
            System.out.printf("DomainLayer.Truck's plate number: %s not found!%n", tPLateNumber);
        }
    }

    public void printAllTrucks(){
        this.truckService.showAllTrucks();
    }

    /**
     * Handles the user input for choosing a truck model from the available options.
     * @param scanner the Scanner object used to read user input
     * @return the index of the chosen truck model in the DomainLayer.TruckModel enum
     * @throws UiException if the user input is invalid
     */
    public int truckModelHandler(Scanner scanner) throws UiException {
        System.out.println("Choose truck model: "); //assuming valid model and weight
        for (TruckModel tm : TruckModel.values()){
            System.out.println(tm.ordinal()+1 + ". " + tm);
        }
        System.out.println("0. exit");
        int ModelOrdinal = scanner.nextInt();
        scanner.nextLine();
        if (0>=ModelOrdinal || ModelOrdinal>TruckModel.values().length)
            throw new UiException("Failed to choose truck model");
        return ModelOrdinal-1;
    }

    /**
     * This function displays a list of available truck qualifications to the user and prompts them to enter a
     * comma-separated list of qualification numbers. It then validates the input and returns an integer array
     * containing the selected qualification numbers.
     * @param scanner the Scanner object used to read input from the console
     * @return an integer array containing the selected qualification numbers
     * @throws UiException if there is an error with the input or the selected qualification numbers are invalid
     */
    public int[] truckQualificationsHandler(Scanner scanner) throws UiException {
        System.out.println("Choose truck qualification (comma-separated, e.g. 1,3,5): ");
        for (Qualification ql : Qualification.values()){
            System.out.println(ql.ordinal()+1 + ". " + ql);
        }
        String sQuali = scanner.nextLine();
        String[] sQualiArr = sQuali.split(",");
        for (String s : sQualiArr) {
            try {
                int num = Integer.parseInt(s.trim());
                if (num < 1 || num > Qualification.values().length) {
                    throw new UiException("Invalid qualification number: " + num);
                }
            } catch (NumberFormatException e) {
                throw new UiException("Invalid input: " + s.trim() + " is not a valid number");
            }
        }
        return toIntArrHelper(sQualiArr);
    }

    /**
     * This function takes a string array and returns an integer array with the values minus 1 of the original array,
     * but with each string converted to an integer. The input array should contain only valid integer strings.
     * @param sArr the string array to be converted
     * @return an integer array with values minus 1 of the input string array
     */
    public int[] toIntArrHelper(String[] sArr){
        int[] intArr = new int[sArr.length];
        for (int i = 0; i < sArr.length; i++) {
            intArr[i] = Integer.parseInt(sArr[i])-1;
        }
        return intArr;
    }
}
