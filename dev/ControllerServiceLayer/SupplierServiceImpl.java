package ControllerServiceLayer;

import DataAccessLayer.SupplierRepository;
import DomainLayer.Supplier;

import java.util.Set;

public class SupplierServiceImpl implements SupplierService{
    private final SupplierRepository supplierRepo;

    public SupplierServiceImpl(SupplierRepository supplierRepo) {
        this.supplierRepo = supplierRepo;
    }

    @Override
    public Set<Supplier> getAllSuppliers() {
        return supplierRepo.findAllSuppliers();
    }

    @Override
    public Supplier findSupplierById(int supplierId) {
        return supplierRepo.findSupplierById(supplierId);
    }

    @Override
    public SupplierRepository getSupplierRepo() {
        return supplierRepo;
    }
}
