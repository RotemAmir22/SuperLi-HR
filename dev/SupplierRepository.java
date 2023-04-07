import java.util.Set;

public interface SupplierRepository {
    void saveSupplier(Supplier supplier);

    Set<Supplier> findAllSuppliers();

    Supplier findSupplierById(int supplierId);
}
