import java.util.Set;

public interface StoreService {
    public Set<Store> getAllStores();
    public Store findStoreById(int storeId);
    public StoreRepository getStoreRepo();
}
