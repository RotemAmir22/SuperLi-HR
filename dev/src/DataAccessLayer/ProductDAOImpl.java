package DataAccessLayer;

import DomainLayer.Product;

import java.util.HashSet;
import java.util.Set;

public class ProductDAOImpl implements ProductDAO {
    private final Set<Product> productsSet = new HashSet<>();


    @Override
    public void saveProduct(Product product) {
        productsSet.add(product);
    }
    @Override
    public void removeProduct(Product product) {
        productsSet.remove(product);
    }
    @Override
    public Set<Product> getProductsSet() {
        return productsSet;
    }
    @Override
    public Product findProductByID(int productID) {
        for (Product product: productsSet){
            if (product.getProductId() == productID)
                return product;
        }
        return null;
    }
}
