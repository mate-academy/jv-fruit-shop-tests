package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageServiceImplTest {

    private StorageServiceImpl storageService;
    private Fruit apple;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImpl();
        apple = new Fruit("apple");
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void addFruit_validStorageUpdating_ok() {
        storageService.addFruit(apple, 50);
        assertEquals(50, Storage.getFruitQuiantity(apple));
    }

    @Test
    void removeFruit_validStorageUpdating_ok() {
        storageService.addFruit(apple, 50);
        storageService.removeFruit(apple, 20);
        assertEquals(30, Storage.getFruitQuiantity(apple));
    }

    @Test
    void removeFruit_NegativeQuantity_notOk() {
        storageService.addFruit(apple, 10);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                storageService.removeFruit(apple, 20));
        assertEquals("Not enough apple in storage to remove", exception.getMessage());
    }
}
