package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageDaoImplTest {
    private static StorageDao storageDao;
    private final int PRODUCT_COUNT = 10;
    private final String PRODUCT_NAME = "apple";

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
    }

    @Before
    public void setUp() {
        Storage.fruitsCount.put(PRODUCT_NAME, PRODUCT_COUNT);
    }

    @After
    public void tearDown() {
        Storage.fruitsCount.clear();
    }

    @Test
    public void addProductCount_countSubZero_notOk() {
        assertThrows(RuntimeException.class, () -> storageDao.addProductCount(PRODUCT_NAME, -5));
    }

    @Test
    public void addProductCount_Ok() {
        assertTrue(Storage.fruitsCount.containsKey(PRODUCT_NAME));
        assertTrue(Storage.fruitsCount.containsValue(PRODUCT_COUNT));
    }

    @Test
    public void getProductCount_Ok() {
        int expected = PRODUCT_COUNT;
        assertEquals(expected, storageDao.getProductCount(PRODUCT_NAME));
    }

    @Test
    public void getProductInfo() {
        List<String> expected = List.of("apple,10");
        assertEquals(expected, storageDao.getProductInfo());
    }

}
