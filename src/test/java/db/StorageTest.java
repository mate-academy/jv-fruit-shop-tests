package db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class StorageTest {
    private static final String NOT_NULL_MESSAGE = "Null can not be add";
    private static final String APPLE = "apple";
    private static final String ORANGE = "orange";
    private static final String NULL = null;
    private static final int TEST_NUMBER_75 = 75;
    private static final int TEST_NUMBER_54 = 54;
    private static final int TEST_NUMBER_0 = 0;
    private static final int TEST_NUMBER_2 = 2;

    @Before
    public void setUp() {
        Storage.clearStorage();
    }

    @Test
    public void putAndGetFruitAmount_validAmount_ok() {
        Storage.storeFruit(APPLE, 10);
        Storage.storeFruit(ORANGE, TEST_NUMBER_75);
        assertEquals(10, Storage.getFruitsNumber(APPLE).intValue());
        assertEquals(TEST_NUMBER_75, Storage.getFruitsNumber(ORANGE).intValue());
    }

    @Test
    public void putFruitToStorage_nullFruit_notOk() {
        assertThrows(RuntimeException.class, () ->
                        Storage.storeFruit(NULL, TEST_NUMBER_75), NOT_NULL_MESSAGE);
    }

    @Test
    public void getFruitAmount_emptyStorage_ok() {
        Storage.clearStorage();
        assertEquals(TEST_NUMBER_0, Storage.getFruitsNumber(APPLE).intValue());
        assertEquals(TEST_NUMBER_0, Storage.getFruitsNumber(ORANGE).intValue());
    }

    @Test
    public void getStorage_validFruits_ok() {
        Storage.storeFruit(APPLE, TEST_NUMBER_75);
        Storage.storeFruit(ORANGE, TEST_NUMBER_54);
        Map<String, Integer> storage = Storage.getStorage();
        assertTrue(storage.containsKey(APPLE));
        assertEquals(TEST_NUMBER_75, (int) storage.get(APPLE));
        assertTrue(storage.containsKey(ORANGE));
        assertEquals(TEST_NUMBER_54, (int) storage.get(ORANGE));
        assertEquals(TEST_NUMBER_2, storage.size());
    }

    @Test
    public void clearStorage_ok() {
        Storage.storeFruit(APPLE, TEST_NUMBER_75);
        Storage.storeFruit(ORANGE, TEST_NUMBER_54);
        Storage.clearStorage();
        assertFalse(Storage.containsFruit(APPLE));
        assertFalse(Storage.containsFruit(ORANGE));
    }

    @Test
    public void containsFruit_validFruit_ok() {
        Storage.storeFruit(APPLE, TEST_NUMBER_75);
        Storage.storeFruit(ORANGE, TEST_NUMBER_54);
        assertTrue(Storage.containsFruit(APPLE));
        assertTrue(Storage.containsFruit(ORANGE));
    }

    @Test
    public void containsFruit_invalidFruit_notOk() {
        Storage.clearStorage();
        Storage.storeFruit(APPLE, TEST_NUMBER_75);
        assertFalse(Storage.containsFruit(ORANGE));
    }
}
