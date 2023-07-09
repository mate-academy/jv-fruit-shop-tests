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
    private String fruit1 = "apple";
    private String fruit2 = "orange";
    private String fruit3 = null;

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
        Storage.putFruitToStorage(fruit1, 10);
        Storage.putFruitToStorage(fruit2, 15);
        Map<String, Integer> storage = new HashMap<>();
        storage = Storage.getStorage();
        assertTrue(storage.containsKey(fruit1));
        assertEquals(10, (long) storage.get(fruit1));
        assertTrue(storage.containsKey(fruit2));
        assertEquals(15, (long) storage.get(fruit2));
        assertEquals(2, storage.size());
    }

    @Test
    public void putFruitToStorage_NullFruit_notOk() {
        assertThrows(RuntimeException.class, () ->
                        Storage.putFruitToStorage(fruit3, 10),
                "Can't add null fruit to storage!");
    }

    @Test
    public void putAndGetFruitAmount_validAmount_Ok() {
        Storage.putFruitToStorage(fruit1, 10);
        Storage.putFruitToStorage(fruit2, 15);
        assertEquals(10, Storage.getFruitAmount(fruit1).intValue());
        assertEquals(15, Storage.getFruitAmount(fruit2).intValue());
    }

    @Test
    public void getFruitAmount_emptyStorage_Ok() {
        Storage.clearStorage();
        assertEquals(0, Storage.getFruitAmount(fruit1).intValue());
        assertEquals(0, Storage.getFruitAmount(fruit2).intValue());
    }

    @Test
    public void clearStorage_Ok() {
        Storage.putFruitToStorage(fruit1, 10);
        Storage.putFruitToStorage(fruit2, 15);
        Storage.clearStorage();
        assertFalse(Storage.containsFruit(fruit1));
        assertFalse(Storage.containsFruit(fruit2));
    }

    @Test
    public void containsFruit_validFruit_Ok() {
        Storage.putFruitToStorage(fruit1, 10);
        Storage.putFruitToStorage(fruit2, 15);
        assertTrue(Storage.containsFruit(fruit1));
        assertTrue(Storage.containsFruit(fruit2));
    }

    @Test
    public void containsFruit_invalidFruit_notOk() {
        Storage.clearStorage();
        Storage.putFruitToStorage(fruit1, 10);
        assertEquals(false, Storage.containsFruit(fruit2));
    }
}
