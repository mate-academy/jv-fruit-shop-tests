package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.service.Product;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProductDaoImplTest {
    private static ProductDao productDao;
    private static final Product PRODUCT = new Product("apple");
    private static final int VALUE = 5;

    @BeforeClass
    public static void before() throws Exception {
        productDao = new ProductDaoImpl();
    }

    @Test
    public void addProduct_Ok() {
        Map<Product, Integer> productsMap = new HashMap<>();
        productsMap.put(PRODUCT, VALUE);
        productDao.add(PRODUCT, VALUE);
        assertEquals(productsMap, Storage.products);
    }

    @Test
    public void getProduct_Ok() {
        Storage.products.put(PRODUCT, VALUE);
        Integer actual = productDao.get(PRODUCT);
        Integer expected = Storage.products.get(PRODUCT);
        assertEquals(actual, expected);
    }

    @Test
    public void getAllProduct_Ok() {
        Map<Product, Integer> actual = productDao.getAll();
        Map<Product, Integer> expected = new HashMap<>();
        assertEquals(actual, expected);
    }

    @Test
    public void nullProductExist_Ok() {
        int actual = productDao.get(PRODUCT);
        assertEquals(0, actual);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.products.clear();
    }
}
