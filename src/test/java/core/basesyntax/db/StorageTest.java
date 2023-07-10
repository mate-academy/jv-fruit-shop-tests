package core.basesyntax.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StorageTest {
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_ORANGE = "orange";
    private static final String FRUIT_NULL = null;

    @Before
    public void setUp() {
        Storage.clearStorage();
    }

    @After
    public void tearDown() {
        Storage.clearStorage();
    }

    @Test
    public void getStorage_validFruits_Ok() {
        Storage.putFruitToStorage(FRUIT_APPLE, 10);
        Storage.putFruitToStorage(FRUIT_ORANGE, 15);
        Map<String, Integer> storage = new HashMap<>();
        storage = Storage.getStorage();
        assertTrue(storage.containsKey(FRUIT_APPLE));
        assertEquals(10, (long) storage.get(FRUIT_APPLE));
        assertTrue(storage.containsKey(FRUIT_ORANGE));
        assertEquals(15, (long) storage.get(FRUIT_ORANGE));
        assertEquals(2, storage.size());
    }

    @Test
    public void putFruitToStorage_NullFruit_notOk() {
        assertThrows(RuntimeException.class, () ->
                        Storage.putFruitToStorage(FRUIT_NULL, 10),
                "Can't add null fruit to storage!");
    }

    @Test
    public void putAndGetFruitAmount_validAmount_Ok() {
        Storage.putFruitToStorage(FRUIT_APPLE, 10);
        Storage.putFruitToStorage(FRUIT_ORANGE, 15);
        assertEquals(10, Storage.getFruitAmount(FRUIT_APPLE).intValue());
        assertEquals(15, Storage.getFruitAmount(FRUIT_ORANGE).intValue());
    }

    @Test
    public void getFruitAmount_emptyStorage_Ok() {
        Storage.clearStorage();
        assertEquals(0, Storage.getFruitAmount(FRUIT_APPLE).intValue());
        assertEquals(0, Storage.getFruitAmount(FRUIT_ORANGE).intValue());
    }

    @Test
    public void clearStorage_Ok() {
        Storage.putFruitToStorage(FRUIT_APPLE, 10);
        Storage.putFruitToStorage(FRUIT_ORANGE, 15);
        Storage.clearStorage();
        assertFalse(Storage.containsFruit(FRUIT_APPLE));
        assertFalse(Storage.containsFruit(FRUIT_ORANGE));
    }

    @Test
    public void containsFruit_validFruit_Ok() {
        Storage.putFruitToStorage(FRUIT_APPLE, 10);
        Storage.putFruitToStorage(FRUIT_ORANGE, 15);
        assertTrue(Storage.containsFruit(FRUIT_APPLE));
        assertTrue(Storage.containsFruit(FRUIT_ORANGE));
    }

    @Test
    public void containsFruit_invalidFruit_notOk() {
        Storage.clearStorage();
        Storage.putFruitToStorage(FRUIT_APPLE, 10);
        assertEquals(false, Storage.containsFruit(FRUIT_ORANGE));
    }
}
