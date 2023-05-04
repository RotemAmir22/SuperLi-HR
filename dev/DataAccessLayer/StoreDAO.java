package DataAccessLayer;

import DomainLayer.Store;

import java.util.Set;

public interface StoreDAO {
    void saveStore(Store store);
    Store findStoreById(int storeId);
    Set<Store> findAllStores();
}
