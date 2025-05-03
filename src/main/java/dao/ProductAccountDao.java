package dao;

import java.util.List;
import java.util.Optional;
import model.ProductAccount;

public interface ProductAccountDao {

    Optional<ProductAccount> add(ProductAccount product);

    Optional<ProductAccount> get(String productName);

    Optional<ProductAccount> update(ProductAccount product);

    List<ProductAccount> getBalance();
}
