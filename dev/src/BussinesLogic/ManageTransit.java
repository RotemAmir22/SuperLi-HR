package BussinesLogic;

import DataAccess.DAO_BranchStore;
import DataAccess.DAO_Drivers;
import DataAccess.DAO_Generator;

public class ManageTransit {

    private DAO_BranchStore branchStoreDAO;
    private DAO_Drivers driversDAO;

    public ManageTransit(){
        branchStoreDAO = DAO_Generator.getBranchStoreDAO();
        driversDAO = DAO_Generator.getDriverDAO();
    }
}
