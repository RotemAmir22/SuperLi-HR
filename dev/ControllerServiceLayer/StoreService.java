package ControllerServiceLayer;

import DataAccessLayer.StoreRepository;
import DomainLayer.Store;

import java.util.Set;

public interface StoreService {
    public Set<Store> getStoresSet();
    public Store findStoreById(int storeId);
    public StoreRepository getStoreRepo();
}
