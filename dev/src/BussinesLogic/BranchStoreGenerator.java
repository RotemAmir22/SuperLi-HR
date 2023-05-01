package BussinesLogic;

public class BranchStoreGenerator {

    public static BranchStore CreateBranchStore(String name, String address, String phoneNum, String openingtime){
        return new BranchStore(name, address, phoneNum, openingtime);
    }

}
