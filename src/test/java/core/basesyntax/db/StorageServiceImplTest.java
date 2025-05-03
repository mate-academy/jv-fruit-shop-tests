package core.basesyntax.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageServiceImplTest {
    private StorageService storageService;

    @BeforeEach
    public void setUp() {
        storageService = new StorageServiceImpl();
        storageService.updateBalance("apple", 10);
        storageService.updateBalance("banana", 8);
    }

    @Test
    public void updateBalance_Ok() {
        storageService.updateBalance("orange", 5);

        assertEquals(5, storageService.getStorage().get("orange").intValue());
    }

    @Test
    public void addSupply_Ok() {
        storageService.addSupply("apple", 5);

        assertEquals(15, storageService.getStorage().get("apple").intValue());
    }

    @Test
    public void purchaseFruit_Ok() {
        storageService.purchaseFruit("apple", 8);

        assertEquals(2, storageService.getStorage().get("apple").intValue());
    }

    @Test
    public void returnFruit_Ok() {
        storageService.returnFruit("apple", 5);

        assertEquals(15, storageService.getStorage().get("apple").intValue());
    }

    @Test
    public void purchaseFruit_InsufficientAmountInStorage_NotOk() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            storageService.purchaseFruit("banana", 10);
        });
        assertEquals("Not enough fruit in stock to complete the purchase.", exception.getMessage());
    }

    @Test
    public void purchaseFruit_FruitNotInStorage_NotOk() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            storageService.purchaseFruit("grapes", 5);
        });
        assertEquals("No such fruit in stock.", exception.getMessage());
    }

    @Test
    public void returnFruit_FruitNotInStock_NotOk() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            storageService.returnFruit("grapes", 5);
        });
        assertEquals("Cannot return fruit that is not in stock.", exception.getMessage());
    }

    @Test
    public void getStorage_Ok() {
        assertEquals(10, storageService.getStorage().get("apple").intValue());
        assertEquals(8, storageService.getStorage().get("banana").intValue());
    }
}
