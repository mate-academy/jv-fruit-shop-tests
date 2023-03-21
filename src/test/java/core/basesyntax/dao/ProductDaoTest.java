package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.impl.ProductDaoImpl;
import core.basesyntax.storage.ProductStorage;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProductDaoTest {
    private static final int EXPECTED_SIZE = 1;
    private static String CORRECT_FRUIT_NAME = "banana";
    private static Integer CORRECT_QUANTITY = 20;
    private static Integer NEGATIVE_QUANTITY = -10;
    private static ProductDao productDao;
    private static Map<String, Integer> expectedProductStorage;

    @BeforeClass
    public static void beforeClass() {
        productDao = new ProductDaoImpl();
        expectedProductStorage = new HashMap<>();
    }

    @Before
    public void clearStorage() {
        ProductStorage.products.clear();
        expectedProductStorage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void updateAmount_nullProductName_notOk() {
        productDao.updateAmount(null, CORRECT_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void updateAmount_nullProductQuantity_notOk() {
        productDao.updateAmount(CORRECT_FRUIT_NAME, null);
    }

    @Test(expected = RuntimeException.class)
    public void updateAmount_negativeProductQuantity_notOk() {
        productDao.updateAmount(CORRECT_FRUIT_NAME, NEGATIVE_QUANTITY);
    }

    @Test
    public void updateAmount_correctProductData_ok() {
        expectedProductStorage.put("banana", 20);
        ProductStorage.products.put("banana", 100);
        productDao.updateAmount(CORRECT_FRUIT_NAME, CORRECT_QUANTITY);
        assertEquals(expectedProductStorage, ProductStorage.products);
    }

    @Test
    public void updateAmount_checkStorageSizeAfterUpdates_ok() {
        productDao.updateAmount(CORRECT_FRUIT_NAME, CORRECT_QUANTITY);
        productDao.updateAmount(CORRECT_FRUIT_NAME, CORRECT_QUANTITY);
        productDao.updateAmount(CORRECT_FRUIT_NAME, CORRECT_QUANTITY);
        int actualSize = ProductStorage.products.size();
        assertEquals(EXPECTED_SIZE, actualSize);
    }

    @Test(expected = RuntimeException.class)
    public void addAmount_nullProductName_notOk() {
        productDao.addAmount(null, CORRECT_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void addAmount_nullProductQuantity_notOk() {
        productDao.addAmount(CORRECT_FRUIT_NAME, null);
    }

    @Test(expected = RuntimeException.class)
    public void addAmount_negativeProductQuantity_notOk() {
        productDao.addAmount(CORRECT_FRUIT_NAME, NEGATIVE_QUANTITY);
    }

    @Test
    public void addAmount_correctProductDataPassedToEmptyStorage_ok() {
        expectedProductStorage.put("banana", 20);
        productDao.addAmount(CORRECT_FRUIT_NAME, CORRECT_QUANTITY);
        assertEquals(expectedProductStorage, ProductStorage.products);
    }

    @Test
    public void addAmount_correctProductDataAddToExistingProducts_ok() {
        expectedProductStorage.put("banana", 40);
        productDao.addAmount(CORRECT_FRUIT_NAME, CORRECT_QUANTITY);
        productDao.addAmount(CORRECT_FRUIT_NAME, CORRECT_QUANTITY);
        assertEquals(expectedProductStorage, ProductStorage.products);
    }

    @Test
    public void addAmount_checkStorageSizeAfterAdding_ok() {
        productDao.addAmount(CORRECT_FRUIT_NAME, CORRECT_QUANTITY);
        productDao.addAmount(CORRECT_FRUIT_NAME, CORRECT_QUANTITY);
        productDao.addAmount(CORRECT_FRUIT_NAME, CORRECT_QUANTITY);
        int actualSize = ProductStorage.products.size();
        assertEquals(EXPECTED_SIZE, actualSize);
    }

    @Test(expected = RuntimeException.class)
    public void subtractAmount_nullProductName_notOk() {
        productDao.subtractAmount(null, CORRECT_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void subtractAmount_nullProductQuantity_notOk() {
        productDao.subtractAmount(CORRECT_FRUIT_NAME, null);
    }

    @Test(expected = RuntimeException.class)
    public void subtractAmount_negativeProductQuantity_notOk() {
        productDao.subtractAmount(CORRECT_FRUIT_NAME, NEGATIVE_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void subtractAmount_subtractingFromEmptyStorage_notOk() {
        productDao.subtractAmount(CORRECT_FRUIT_NAME, CORRECT_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void subtractAmount_subtractingMoreProductsThanExists_notOk() {
        ProductStorage.products.put("banana", 19);
        productDao.subtractAmount(CORRECT_FRUIT_NAME, CORRECT_QUANTITY);
    }

    @Test
    public void subtractAmount_correctProductData_ok() {
        expectedProductStorage.put("banana", 20);
        ProductStorage.products.put("banana", 40);
        productDao.subtractAmount(CORRECT_FRUIT_NAME, CORRECT_QUANTITY);
        assertEquals(expectedProductStorage, ProductStorage.products);
    }
}
