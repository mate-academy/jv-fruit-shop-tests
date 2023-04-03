package core.basesyntax;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Product;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProductDaoTests {
    private static ProductDao productDao;

    @BeforeClass
    public static void setProductDao() {
        productDao = new ProductDaoImpl();
    }

    @After
    public void afterEachTest() {
        Storage.products.clear();
    }

    @Test(expected = NullPointerException.class)
    public void addNullProduct_NotOk() {
        productDao.add(null);
    }

    @Test
    public void addProduct_Ok() {
        Product product = new Product("banana", 100);
        productDao.add(product);
        Assert.assertTrue(Storage.products.contains(product));
    }

    @Test
    public void getProduct_Ok() {
        Product product = new Product("banana", 100);
        productDao.add(product);
        Product actual = productDao.get(product.getName());
        Assert.assertEquals(product, actual);
    }

    @Test
    public void getNonExistProduct_NotOk() {
        String productName = "banana";
        try {
            Product expected = productDao.get(productName);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void getAllProducts_Ok() {
        Product productBanana = new Product("banana", 100);
        Product productApple = new Product("apple", 50);
        productDao.add(productBanana);
        productDao.add(productApple);
        List<Product> expected = List.of(productBanana, productApple);
        List<Product> actual = productDao.getAll();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateExistProduct_Ok() {
        Product product = new Product("banana", 100);
        Product updatedProduct = new Product("banana", 50);
        productDao.add(product);
        productDao.update(updatedProduct);
        Product actual = productDao.get("banana");
        Assert.assertEquals(updatedProduct, actual);
    }

    @Test(expected = RuntimeException.class)
    public void updateNonExistProduct_NotOk() {
        Product product = new Product("banana", 100);
        productDao.update(product);
    }
}
