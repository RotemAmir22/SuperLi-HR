package ControllerServiceLayer;

import DataAccessLayer.ProductRepository;
import DataAccessLayer.StoreRepository;
import DomainLayer.Product;
import DomainLayer.Store;

import java.util.Set;

public interface ProductService {
    public Set<Product> getAllProducts();
    public Product findProductById(int productId);
    public Product findProductByname(String productName);

    public ProductRepository getProductRepo();
    public void showAllProducts();
}
