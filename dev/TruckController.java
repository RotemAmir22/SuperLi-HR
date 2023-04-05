public class TruckController {
    public Truck CreateTruck(String plateNumber, String sModel, String sQuali, double truckWeight, double maxWeight){
        TruckModel tModel;
        Qualification qNeeded;
        switch(sModel.toUpperCase()) {
            case "SMALLTRUCK":
                tModel = TruckModel.SMALLTRUCK;
                break;
            case "LARGETRUCK":
                tModel = TruckModel.LARGETRUCK;
                break;
            /**
             * WORK ON THE LOGICS LATER - consider truck weight
             */
            default:
                tModel = TruckModel.SMALLTRUCK;
        }

        switch(sQuali.toUpperCase()) {
            case "COOLER":
                qNeeded = Qualification.COOLER;
                break;
            /**
             * WORK ON THE LOGICS LATER
             */
            default:
                qNeeded = null;
        }
        Truck newTruck = new Truck(plateNumber, tModel, truckWeight, maxWeight, qNeeded);
        return newTruck;
    }


}
