package core.basesyntax.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class StorageServiceImplTest {
    private StorageService storageService;

    @Before
    public void setUp() {
        storageService = new StorageServiceImpl();
    }

    @Test
    public void updateBalance_Ok() {
        storageService.updateBalance("apple", 12);

        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 12);

        assertEquals(expected, storageService.getStorage());
    }

    @Test
    public void addSupply_Ok() {
        storageService.updateBalance("apple", 10);
        storageService.addSupply("apple", 5);

        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 15);

        assertEquals(expected, storageService.getStorage());
    }

    @Test
    public void purchaseFruit_Ok() {
        storageService.updateBalance("apple", 10);
        storageService.purchaseFruit("apple", 5);

        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 5);

        assertEquals(expected, storageService.getStorage());
    }

    @Test
    public void returnFruit_Ok() {
        storageService.updateBalance("apple", 10);
        storageService.returnFruit("apple", 5);

        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 15);

        assertEquals(expected, storageService.getStorage());
    }

    @Test
    public void getStorage_Ok() {
        storageService.updateBalance("apple", 10);
        storageService.addSupply("banana", 5);

        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("apple", 10);
        expectedStorage.put("banana", 5);
        assertEquals(expectedStorage, storageService.getStorage());
    }

    @Test
    public void getStorage_nullStorage_Ok() {
        Map<String, Integer> expectedStorage = new HashMap<>();
        assertEquals(expectedStorage, storageService.getStorage());
    }

    @Test
    public void purchaseFruit_noSuchFruit_NotOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            storageService.purchaseFruit("banana", 5);
        });
        assertEquals("No such fruit in stock.", exception.getMessage());
    }

    @Test
    public void purchaseFruit_NotOk_NotEnoughInStock() {
        storageService.updateBalance("apple", 5);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            storageService.purchaseFruit("apple", 10);
        });
        assertEquals("Not enough fruit in stock to complete the purchase.", exception.getMessage());
    }
}
