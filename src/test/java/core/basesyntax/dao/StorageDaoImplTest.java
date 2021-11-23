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
    private int productCount = 10;
    private String productName = "apple";

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
    }

    @Before
    public void setUp() {
        Storage.fruitsCount.put(productName, productCount);
    }

    @After
    public void tearDown() {
        Storage.fruitsCount.clear();
    }

    @Test
    public void addProductCount_countSubZero_notOk() {
        assertThrows(RuntimeException.class, () -> storageDao.addProductCount(productName, -5));
    }

    @Test
    public void addProductCount_Ok() {
        assertTrue(Storage.fruitsCount.containsKey(productName));
        assertTrue(Storage.fruitsCount.containsValue(productCount));
    }

    @Test
    public void getProductCount_Ok() {
        int expected = productCount;
        assertEquals(expected, storageDao.getProductCount(productName));
    }

    @Test
    public void getProductInfo() {
        List<String> expected = List.of("apple,10");
        assertEquals(expected, storageDao.getProductInfo());
    }

}
