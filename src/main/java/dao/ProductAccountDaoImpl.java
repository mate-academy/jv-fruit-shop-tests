package dao;

import db.Storage;
import java.util.List;
import java.util.Optional;
import model.ProductAccount;

public class ProductAccountDaoImpl implements ProductAccountDao {
    private Storage db;

    public ProductAccountDaoImpl(Storage db) {
        this.db = db;
    }

    @Override
    public Optional<ProductAccount> add(ProductAccount product) {
        db.products.add(product);
        return null;
    }

    @Override
    public Optional<ProductAccount> get(String productName) {
        return db.products.stream()
                .filter(a -> a.getName().equals(productName))
                .findFirst();
    }

    @Override
    public Optional<ProductAccount> update(ProductAccount product) {
        Optional<ProductAccount> optProductAccount = get(product.getName());
        if (optProductAccount.isPresent()) {
            ProductAccount productFromDb = optProductAccount.get();
            db.products.remove(productFromDb);
        }
        return add(product);
    }

    @Override
    public List<ProductAccount> getBalance() {
        return db.products;
    }
}
