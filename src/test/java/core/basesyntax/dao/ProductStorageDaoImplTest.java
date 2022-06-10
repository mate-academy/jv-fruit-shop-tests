package core.basesyntax.dao;

import core.basesyntax.db.ProductStorage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProductStorageDaoImplTest {
    private static ProductStorageDao productStorageDao;
    private static Map<String, Integer> testStorage;

    @BeforeClass
    public static void beforeClass() {
        testStorage = new HashMap<>();
        productStorageDao = new ProductStorageDaoImpl();
    }

    @Before
    public void setUp() {
        testStorage.put("banana", 100);
        testStorage.put("apple", 150);
        testStorage.put("mango", 200);
        productStorageDao.setQuantity("banana", 100);
        productStorageDao.setQuantity("apple", 150);
        productStorageDao.setQuantity("mango", 200);
    }

    @Test
    public void setQuantity_ok() {
        Assert.assertEquals(testStorage, ProductStorage.products);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void getQuantity_ok() {
        Assert.assertEquals(Integer.valueOf(100),
                productStorageDao.getQuantity("banana").get());
        Assert.assertEquals(Integer.valueOf(150),
                productStorageDao.getQuantity("apple").get());
        Assert.assertEquals(Integer.valueOf(200),
                productStorageDao.getQuantity("mango").get());
    }

    @Test
    public void getAll_ok() {
        Assert.assertEquals(testStorage, productStorageDao.getAll());
    }

    @After
    public void tearDown() {
        testStorage.clear();
        ProductStorage.products.clear();
    }
}
