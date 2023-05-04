package DataAccessLayer;

import DomainLayer.Store;

import java.util.HashSet;
import java.util.Set;

public class StoreDAOImpl implements StoreDAO {
    private final Set<Store> storesSet = new HashSet<>();


    @Override
    public void saveStore(Store store) {
        storesSet.add(store);}
    @Override
    public Set<Store> findAllStores() {return storesSet;}
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