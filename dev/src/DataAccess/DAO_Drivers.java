package DataAccess;

import BussinesLogic.Driver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAO_Drivers implements DAO{
    private Map<String, Driver> networkDrivers;

    // constructor
    public DAO_Drivers() {
        networkDrivers = new HashMap<>();
    }

    @Override
    public Object findByID(Object ID) {
        if (networkDrivers.containsKey(ID)){return networkDrivers.get(ID);}
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

    public List<Driver> getNetworkDrivers(){
        return (List<Driver>) networkDrivers.values();
    }
}
