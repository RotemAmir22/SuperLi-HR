package DataAccess;

import BussinesLogic.BranchStore;
import BussinesLogic.DailyShift;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class DAO_DailyShift implements IDAO_DailyShift {

    private ArrayList<DailyShift> networkDailyShift;
    @Override
    public Object findByKey(Object date, Object id) throws SQLException, ClassNotFoundException {
        DailyShift dailyShift = new DailyShift((LocalDate) date);
        DAO_BranchStore daoBranchStore = DAO_Generator.getBranchStoreDAO();
        BranchStore branchStore = (BranchStore) daoBranchStore.findByID(id);
        if(daoBranchStore.findByID(id) != null){
        }


        return null;
    }

    @Override
    public void insert(Object o) throws SQLException {

    }

    @Override
    public void update(Object o) throws SQLException {

    }

    @Override
    public void delete(Object o) throws SQLException {

    }
}
