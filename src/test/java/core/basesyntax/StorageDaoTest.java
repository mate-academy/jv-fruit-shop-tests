package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageDaoTest {
    public static final int VALID_FIRST_QUANTITY = 100;
    public static final int VALID_SECOND_QUANTITY = 200;
    public static final int VALID_THIRD_QUANTITY = 333;
    public static final int VALID_FOURTH_QUANTITY = 404;
    public static final int INVALID_FIRST_QUANTITY = -1;
    public static final String VALID_FIRST_FRUIT = "banana";
    public static final String VALID_SECOND_FRUIT = "apple";
    public static final String VALID_THIRD_FRUIT = "lemon";
    public static final String VALID_FOURTH_FRUIT = "strawberry";

    private static StorageDao storageDao;
    private static Map<String, Integer> storageRecords;

    @BeforeClass
    public static void beforeAll() {
        storageDao = new StorageDaoImpl();
    }

    @Before
    public void setUp() {
        Storage.records.clear();
    }

    @Test
    public void daoAdd_oneElement_Ok() {
        storageDao.push(VALID_FIRST_FRUIT, VALID_FIRST_QUANTITY);
        long fruitQuantity = Storage.records.get(VALID_FIRST_FRUIT);
        assertEquals("One element added to storage not equal", VALID_FIRST_QUANTITY, fruitQuantity);
    }

    @Test
    public void daoAdd_negativeBalance_notOk() {
        assertThrows(RuntimeException.class, () -> {
            storageDao.push(VALID_FIRST_FRUIT, INVALID_FIRST_QUANTITY);
        });
    }

    @Test
    public void testGetAll() {
        storageRecords = new HashMap<>();
        storageRecords.put(VALID_FIRST_FRUIT, VALID_FIRST_QUANTITY);
        storageRecords.put(VALID_SECOND_FRUIT, VALID_SECOND_QUANTITY);
        storageRecords.put(VALID_THIRD_FRUIT, VALID_THIRD_QUANTITY);
        storageRecords.put(VALID_FOURTH_FRUIT, VALID_FOURTH_QUANTITY);
        Storage.records.putAll(storageRecords);
        assertEquals("Items not equal in storage ", storageDao.getAll(), storageRecords);
    }
}
