package BussinesLogic;

public class BranchStoreGenerator {

    public static BranchStore CreateBranchStore(String name, Area area, String address, String phoneNum, String openingtime){
        return new BranchStore(name, area, address, phoneNum, openingtime);
    }

}
