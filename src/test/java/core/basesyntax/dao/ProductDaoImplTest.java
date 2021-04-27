package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.Product;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProductDaoImplTest {
    private static ProductDao productDao;
    private static final Product PRODUCT = new Product("banana");
    private static final int AMOUNT = 15;

    @BeforeClass
    public static void beforeClass() throws Exception {
        productDao = new ProductDaoImpl();
    }

    @Test
    public void addTest_Ok() {
        Map<Product, Integer> productsMap = new HashMap<>();
        productsMap.put(PRODUCT, AMOUNT);
        productDao.add(PRODUCT, AMOUNT);
        assertEquals(productsMap, Storage.getProducts());
    }

    @Test
    public void getTest_Ok() {
        Storage.getProducts().put(PRODUCT, AMOUNT);
        Integer actual = productDao.get(PRODUCT);
        Integer expected = Storage.getProducts().get(PRODUCT);
        assertEquals(expected, actual);
    }

    @Test
    public void getNotExistingProduct_Ok() {
        int actual = productDao.get(PRODUCT);
        assertEquals(0, actual);
    }

    @Test
    public void getAllTest_Ok() {
        Map<Product, Integer> expected = new HashMap<>();
        Map<Product, Integer> actual = productDao.getAll();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.getProducts().clear();
    }
}
