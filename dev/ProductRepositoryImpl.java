import java.util.HashSet;
import java.util.Set;

public class ProductRepositoryImpl implements ProductRepository {
    private final Set<Product> products = new HashSet<>();
    @Override
    public void saveProduct(Product product) {
        products.add(product);
    }

    @Override
    public void removeProduct(Product product) {
        products.remove(product);
    }

    @Override
    public Set<Product> getProductsSet() {
        return products;
    }

    @Override
    public Product findProductByID(int productID) {
        for (Product product: products){
            if (product.getProductId() == productID)
                return product;
        }
        return null;
    }
}
