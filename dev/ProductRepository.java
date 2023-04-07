import java.util.Set;

public interface ProductRepository {
    void saveProduct(Product product);
    void removeProduct(Product product);
    Set<Product> getProductsSet();
    Product findProductByID(int productID);
}
