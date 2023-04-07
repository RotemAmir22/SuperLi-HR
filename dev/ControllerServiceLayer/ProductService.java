package ControllerServiceLayer;

import DataAccessLayer.ProductRepository;
import DomainLayer.Product;

import java.util.Set;

public interface ProductService {
    public Set<Product> getProductsSet();
    public Product findProductById(int productId);
    public Product findProductByName(String productName);

    public ProductRepository getProductRepo();
    public void showAllProducts();
}
