package DataAccess;

import Module_HR.BranchStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAO_BranchStore implements DAO{

    private Map<Integer,BranchStore> networkBranches;

    // constructor
    public DAO_BranchStore() {
        networkBranches = new HashMap<>();
    }

    public BranchStore findBranchByID(int ID) {

        if (networkBranches.containsKey(ID)){return networkBranches.get(ID);}
        return null;
    }


    @Override
    public void insert(Object o) {

    }

    @Override
    public void update(Object o) {

    }

    @Override
    public void delete(Object o) {

    }

    public List<BranchStore> getNetworkBranches(){
        return (List<BranchStore>) networkBranches.values();
    }
}
