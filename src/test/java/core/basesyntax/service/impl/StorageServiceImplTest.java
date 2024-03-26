package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidFruitException;
import core.basesyntax.service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageServiceImplTest {
    private static final String NOT_EXIST_FRUIT = "grape";
    private static final String VALID_FRUIT_BANANA = "banana";
    private static final String VALID_FRUIT_APPLE = "apple";

    private StorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImpl(new Storage());
    }

    @Test
    void getQuantity_NonExistingFruit_ThrowsException() {
        InvalidFruitException exception = assertThrows(
                InvalidFruitException.class,
                () -> storageService.getQuantity(NOT_EXIST_FRUIT)
        );
        assertEquals("We don't have that fruit "
                + NOT_EXIST_FRUIT + " in the storage", exception.getMessage());
    }

    @Test
    void add_AddFruit() {
        storageService.add(VALID_FRUIT_BANANA,20);
        assertEquals(20, storageService.getQuantity(VALID_FRUIT_BANANA));
    }

    @Test
    void updateQuantity_ExistingFruit_UpdatesQuantity() {
        storageService.add(VALID_FRUIT_APPLE, 10);
        storageService.updateQuantity(VALID_FRUIT_APPLE, 15);
        assertEquals(15, storageService.getQuantity(VALID_FRUIT_APPLE));
    }

    @Test
    void updateQuantity_NonExistingFruit_AddsFruit() {
        storageService.updateQuantity(VALID_FRUIT_BANANA, 30);
        assertEquals(30, storageService.getQuantity(VALID_FRUIT_BANANA));
    }

    @Test
    void getAll_ReturnsAllFruits() {
        storageService.add(VALID_FRUIT_APPLE, 10);
        storageService.add(VALID_FRUIT_BANANA, 20);

        Map<String, Integer> expected = new HashMap<>();
        expected.put(VALID_FRUIT_APPLE, 10);
        expected.put(VALID_FRUIT_BANANA, 20);

        assertEquals(expected, storageService.getAll());
    }
}
