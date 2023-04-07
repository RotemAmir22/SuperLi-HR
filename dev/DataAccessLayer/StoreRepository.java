package DataAccessLayer;

import DomainLayer.Store;

import java.util.Set;

public interface StoreRepository {
    void saveStore(Store store);

    Set<Store> findAllStores();

    Store findStoreById(int storeId);
}
