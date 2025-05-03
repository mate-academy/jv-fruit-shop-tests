package service;

import java.util.Optional;
import model.ProductAccount;

public interface ProductAccountService {
    Optional<ProductAccount> createNewProduct(String productname);
}
