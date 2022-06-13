package core.basesyntax.dao;

import core.basesyntax.db.ProductStorage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProductDaoImplTest {
    private static ProductDao productDao;
    private static Map<String, Integer> testStorage;

    @BeforeClass
    public static void beforeClass() {
        testStorage = new HashMap<>();
        productDao = new ProductDaoImpl();
    }

    @Before
    public void setUp() {
        testStorage.put("banana", 100);
        testStorage.put("apple", 150);
        testStorage.put("mango", 200);
        productDao.setQuantity("banana", 100);
        productDao.setQuantity("apple", 150);
        productDao.setQuantity("mango", 200);
    }

    @Test
    public void setQuantity_ok() {
        Assert.assertEquals(testStorage, ProductStorage.products);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void getQuantity_ok() {
        Assert.assertEquals(Integer.valueOf(150),
                productDao.getQuantity("apple").get());
        Assert.assertEquals(Integer.valueOf(200),
                productDao.getQuantity("mango").get());
    }

    @Test
    public void getAll_ok() {
        Assert.assertEquals(testStorage, productDao.getAll());
    }

    @After
    public void tearDown() {
        testStorage.clear();
        ProductStorage.products.clear();
    }
}
