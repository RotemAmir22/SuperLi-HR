package DataAccessLayer;

import DomainLayer.Supplier;

import java.util.HashSet;
import java.util.Set;

public class SupplierDAOImpl implements SupplierDAO {
    private final Set<Supplier> suppliersSet = new HashSet<>();


    @Override
    public void saveSupplier(Supplier supplier){
        suppliersSet.add(supplier);}
    @Override
    public Set<Supplier> findAllSuppliers(){return suppliersSet;}
    @Override
    public Supplier findSupplierById(int supplierId){
        for (Supplier supplier : this.findAllSuppliers()) {
            if (supplier.getSupplierID() == supplierId) {
                return supplier;
            }
        }
        return null;
    }
}