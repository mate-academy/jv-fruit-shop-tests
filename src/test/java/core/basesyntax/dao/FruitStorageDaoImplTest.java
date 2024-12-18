package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dp.Storage;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitStorageDaoImplTest {

    private FruitStorageDaoImpl fruitStorageDao;

    @BeforeEach
    public void setUp() {
        fruitStorageDao = new FruitStorageDaoImpl();
    }

    @AfterEach
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void testSetQuantity_ok() {
        fruitStorageDao.setQuantity("apple", 50);

        assertEquals(50, Storage.storage.get("apple"), "The quantity of apple should be set to 50");
    }

    @Test
    public void testGetQuantity_ok() {
        Storage.storage.put("banana", 30);

        Integer quantity = fruitStorageDao.getQuantity("banana");

        assertEquals(30, quantity, "The quantity of banana should be 30");
    }

    @Test
    public void testGetQuantity_notOk() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fruitStorageDao.getQuantity("orange"),
                "Expected exception for missing fruit"
        );

        assertTrue(exception.getMessage().contains("There is no orange in the storage"),
                "Exception message should mention missing fruit");
    }

    @Test
    public void testGetAll_ok() {
        Storage.storage.put("apple", 50);
        Storage.storage.put("banana", 30);

        Set<Map.Entry<String, Integer>> allEntries = fruitStorageDao.getAll();

        assertEquals(2, allEntries.size(), "There should be two entries in the storage");
        assertTrue(allEntries.contains(Map.entry("apple", 50)),
                "Storage should contain apple with quantity 50");
        assertTrue(allEntries.contains(Map.entry("banana", 30)),
                "Storage should contain banana with quantity 30");
    }

    @Test
    public void testGetAll_emptyStorage_ok() {
        Set<Map.Entry<String, Integer>> allEntries = fruitStorageDao.getAll();

        assertTrue(allEntries.isEmpty(), "Storage should be empty");
    }
}
