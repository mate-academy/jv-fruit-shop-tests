package service;

import dao.ProductAccountDaoImpl;
import java.util.Optional;
import model.ProductAccount;

public class ProductAccountServiceImpl implements ProductAccountService {
    private ProductAccountDaoImpl dao;

    public ProductAccountServiceImpl(ProductAccountDaoImpl dao) {
        this.dao = dao;
    }

    @Override
    public Optional<ProductAccount> createNewProduct(String productname) {
        dao.add(new ProductAccount(productname));
        return dao.get(productname);
    }
}
