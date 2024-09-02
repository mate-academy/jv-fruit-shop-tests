package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.StorageService;
import core.basesyntax.service.impl.StorageServiceImpl;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageServiceImplTest {
    private static final String APPLE = "apple";
    private static final int APPLE_QUANTITY = 10;
    private static final int APPLE_NEGATIVE_QUANTITY = -5;
    private static final String BANANA = "banana";
    private static final int BANANA_QUANTITY = 10;
    private StorageService storageService;

    @BeforeEach
    void setUp() {
        Storage.getAllFruits();
        storageService = new StorageServiceImpl();
    }

    @Test
    void testAddFruit_validInput_ok() {
        storageService.addFruit(APPLE, APPLE_QUANTITY);
        Map<String, Integer> fruits = storageService.getAllFruits();
        Integer actual = fruits.get(APPLE);
        assertEquals(130, actual);
    }

    @Test
    void testAddFruit_nullFruit_notOk() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> storageService.addFruit(null, APPLE_QUANTITY)
        );
        assertEquals("Fruit name cannot be null or empty", thrown.getMessage());
    }

    @Test
    void testAddFruit_negativeQuantity_notOk() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> storageService.addFruit(APPLE, APPLE_NEGATIVE_QUANTITY)
        );
        assertEquals("Quantity cannot be negative", thrown.getMessage());
    }

    @Test
    void testAddFruit_emptyFruit_notOk() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> storageService.addFruit("", APPLE_QUANTITY),
                "Expected addFruit() to"
                        + " throw IllegalArgumentException when fruit is empty"
        );
        assertEquals("Fruit name cannot be null or empty", thrown.getMessage());
    }

    @Test
    void testRemoveFruit_validInput_ok() {
        storageService.addFruit(BANANA, BANANA_QUANTITY);
        storageService.removeFruit(BANANA, BANANA_QUANTITY);
        Map<String, Integer> fruits = storageService.getAllFruits();
        assertEquals(null, fruits.get("grape"));
    }

    @Test
    void testRemoveFruit_nullFruit_notOk() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> storageService.removeFruit(null, APPLE_QUANTITY),
                "Expected removeFruit() to"
                        + " throw IllegalArgumentException when fruit is null"
        );
        assertEquals("Fruit name cannot be null or empty", thrown.getMessage());
    }

    @Test
    void testRemoveFruit_emptyFruit_notOk() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> storageService.removeFruit("", APPLE_QUANTITY),
                "Expected removeFruit() to"
                        + " throw IllegalArgumentException when fruit is empty"
        );
        assertEquals("Fruit name cannot be null or empty", thrown.getMessage());
    }

    @Test
    void testRemoveFruit_negativeQuantity_notOk() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> storageService.removeFruit(APPLE, APPLE_NEGATIVE_QUANTITY),
                "Expected removeFruit() to"
                        + " throw IllegalArgumentException when quantity is negative"
        );
        assertEquals("Quantity cannot be negative", thrown.getMessage());
    }

    @Test
    void testRemoveFruit_notEnoughQuantity_notOk() {
        Storage storage = new Storage();
        storage.addFruit(APPLE, APPLE_QUANTITY);
        assertThrows(RuntimeException.class,
                () -> storageService.removeFruit(APPLE, 200));
    }

    @Test
    void testGetAllFruits_valid_ok() {
        storageService.addFruit(APPLE, APPLE_QUANTITY);
        Map<String, Integer> fruits = storageService.getAllFruits();
        assertNotNull(fruits);
        assertEquals(140, fruits.get(APPLE));
    }
}
