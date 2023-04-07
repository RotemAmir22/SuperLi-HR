package ControllerServiceLayer;

import DataAccessLayer.StoreRepository;
import DomainLayer.Store;

import java.util.Set;

public class StoreServiceImpl implements StoreService{
    private final StoreRepository storeRepo;

    public StoreServiceImpl(StoreRepository storeRepo) {
        this.storeRepo = storeRepo;
    }

    @Override
    public Set<Store> getStoresSet() {
        return storeRepo.findAllStores();
    }

    @Override
    public Store findStoreById(int storeId) {
        return storeRepo.findStoreById(storeId);
    }

    @Override
    public StoreRepository getStoreRepo() {
        return storeRepo;
    }
}