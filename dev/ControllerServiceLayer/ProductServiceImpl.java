package ControllerServiceLayer;

import DataAccessLayer.ProductRepository;
import DomainLayer.Product;
import DomainLayer.Supplier;

import java.util.Set;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;

    public ProductServiceImpl(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public Set<Product> getProductsSet() {
        return productRepo.getProductsSet();
    }

    @Override
    public Product findProductById(int productId) {
        return productRepo.findProductByID(productId);
    }

    @Override
    public Product findProductByName(String productName) {
        return null;
    }

    @Override
    public ProductRepository getProductRepo() {
        return productRepo;
    }

    @Override
    public void showAllProducts() {
        for (Product product: productRepo.getProductsSet())
        {
            product.printProduct();
        }
    }
}