package service;

import dao.ProductAccountDaoImpl;
import db.Storage;
import model.ProductAccount;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ProductAccountServiceImplTest {

    @Test
    void createNewProduct() {
        Storage memdb = new Storage();
        memdb.products.clear();
        ProductAccountDaoImpl dao = new ProductAccountDaoImpl(memdb);
        ProductAccountServiceImpl productAccountService = new ProductAccountServiceImpl(dao);
        ProductAccount product =
                productAccountService.createNewProduct("ProductCreatedBySercie").get();
        Assert.assertEquals("Product is equal",product,memdb.products.get(0));
        Assert.assertEquals("",Integer.valueOf(14), product.setAmount(14).getAmount());

    }
}

