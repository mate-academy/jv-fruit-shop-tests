package core.basesyntax.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InsufficientStockException;
import core.basesyntax.exceptions.ProductIsAbsentException;
import core.basesyntax.service.StorageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageServiceImplTest {
    private StorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImpl();
    }

    @Test
    void add_validData_ok() {
        storageService.add("banana", 20);
        assertEquals(20, Storage.STORAGE.get("banana"));
    }

    @Test
    void add_notValidData_notOk() {
        assertThrows(RuntimeException.class, () -> storageService.add(null, 20));
    }

    @Test
    void update_validData_ok() {
        storageService.add("banana", 20);
        storageService.update("banana", 5);
        assertEquals(25, Storage.STORAGE.get("banana"));
    }

    @Test
    void update_notValidData_notOk() {
        assertThrows(RuntimeException.class, () -> storageService.update(null, 20));
    }

    @Test
    void remove_validData_ok() {
        storageService.add("banana", 20);
        storageService.remove("banana", 5);
        assertEquals(15, Storage.STORAGE.get("banana"));
    }

    @Test
    void remove_notEnoughQuantity_notOk() {
        storageService.add("banana", 20);
        assertThrows(InsufficientStockException.class, () -> storageService.remove("banana", 25));
    }

    @Test
    void remove_notValidData_notOk() {
        storageService.add("banana", 20);
        assertThrows(RuntimeException.class, () -> storageService.remove(null, 25));
    }

    @Test
    void remove_noProductInStorage_notOk() {
        storageService.add("banana", 20);
        assertThrows(ProductIsAbsentException.class, () -> storageService.remove("apple", 25));
    }

    @Test
    void remove_negativeValue_notOk() {
        storageService.add("banana", 20);
        assertThrows(RuntimeException.class, () -> storageService.remove("banana", -25));
    }

    @Test
    void get_validData_ok() {
        storageService.add("banana", 20);
        assertEquals(storageService.get("banana"), Storage.STORAGE.get("banana"));
    }

    @Test
    void get_notValidData_notOk() {
        assertThrows(RuntimeException.class, () -> storageService.get(null));
    }

    @AfterEach
    public void afterEachTest() {
        Storage.STORAGE.clear();
    }
}
