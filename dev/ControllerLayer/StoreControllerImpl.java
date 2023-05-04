package ControllerLayer;

import DataAccessLayer.StoreDAO;
import DomainLayer.Store;

public class StoreControllerImpl implements StoreController {
    private final StoreDAO storeDAO;


    public StoreControllerImpl(StoreDAO storeDAO) {
        this.storeDAO = storeDAO;
    }
    @Override
    public Store findStoreById(int storeId) {
        return storeDAO.findStoreById(storeId);
    }
}