package DataAccessLayer;

import DomainLayer.Store;

import java.util.HashSet;
import java.util.Set;

public class StoreRepositoryImpl implements StoreRepository {
    private final Set<Store> stores = new HashSet<>();


    @Override
    public void saveStore(Store store) {stores.add(store);}

    @Override
    public Set<Store> findAllStores() {return stores;}

    @Override
    public Store findStoreById(int storeId) {
        for (Store store :this.findAllStores()) {
            if (store.getStoreId() == storeId) {
                return store;
            }
        }
        return null;
    }
}