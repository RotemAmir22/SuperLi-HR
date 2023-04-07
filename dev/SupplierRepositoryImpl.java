import java.util.HashSet;
import java.util.Set;

public class SupplierRepositoryImpl implements SupplierRepository{
    private final Set<Supplier> suppliers = new HashSet<>();
    @Override
    public void saveSupplier(Supplier supplier){suppliers.add(supplier);}
    @Override
    public Set<Supplier> findAllSuppliers(){return suppliers;}

    /**
     * @param supplierId
     * search function for specific suppliers and stores
     * @return supplier
     */
    @Override
    public Supplier findSupplierById(int supplierId){
        for (Supplier supplier : this.findAllSuppliers()) {
            if (supplier.getSupplierID() == supplierId) {
                return supplier;
            }
        }
        return null; //not found
    }
}