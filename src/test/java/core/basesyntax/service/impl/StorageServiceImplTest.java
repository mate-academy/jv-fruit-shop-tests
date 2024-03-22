package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.StorageService;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageServiceImplTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String NON_EXISTENT_FRUIT = "orange";
    private static final int BANANA_QUANTITY = 50;
    private static final int APPLE_QUANTITY = 35;
    private static final int NEGATIVE_QUANTITY = -15;
    private static final String QUANTITY_ERROR_MESSAGE =
            "Quantity must be a non-negative value, but you provided: ";
    private StorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImpl();
    }

    @Test
    void add_validDataToStorage_Ok() {
        storageService.add(BANANA, BANANA_QUANTITY);
        assertEquals(BANANA_QUANTITY, storageService.get(BANANA));
    }

    @Test
    void add_NegativeQuantityToStorage_NotOk() {
        String expectedMessage = QUANTITY_ERROR_MESSAGE + NEGATIVE_QUANTITY;
        var exception = assertThrows(IllegalArgumentException.class, () ->
                storageService.add(BANANA, NEGATIVE_QUANTITY));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void add_ExistingKeyToStorage_Ok() {
        int expectedQuantity = 25;
        storageService.add(BANANA, BANANA_QUANTITY);
        storageService.add(BANANA, expectedQuantity);
        assertEquals(expectedQuantity, storageService.get(BANANA));
    }

    @Test
    void add_NullFruitNameToStorage_Ok() {
        storageService.add(null, BANANA_QUANTITY);
        assertEquals(BANANA_QUANTITY, storageService.get(null));
    }

    @Test
    void add_NullQuantityToStorage_NotOk() {
        String expectedMessage = QUANTITY_ERROR_MESSAGE + null;
        var exception = assertThrows(IllegalArgumentException.class, () ->
                storageService.add(BANANA, null));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void get_ValidDataFromStorage_Ok() {
        storageService.add(BANANA, BANANA_QUANTITY);
        storageService.add(APPLE, APPLE_QUANTITY);
        assertEquals(BANANA_QUANTITY, storageService.get(BANANA));
        assertEquals(APPLE_QUANTITY, storageService.get(APPLE));
    }

    @Test
    void get_NonExistentDataFromStorage_NotOk() {
        storageService.add(BANANA, BANANA_QUANTITY);
        storageService.add(APPLE, APPLE_QUANTITY);

        assertNotNull(storageService.get(BANANA));
        assertNotNull(storageService.get(APPLE));
        assertNull(storageService.get(NON_EXISTENT_FRUIT));
    }

    @Test
    void getAll_ValidDataFromStorage_Ok() {
        storageService.add(BANANA, BANANA_QUANTITY);
        storageService.add(APPLE, APPLE_QUANTITY);

        Map<String, Integer> allProducts = storageService.getAll();
        assertEquals(BANANA_QUANTITY, allProducts.get(BANANA));
        assertEquals(APPLE_QUANTITY, allProducts.get(APPLE));
    }

    @Test
    void getAll_EmptyDataFromStorage_Ok() {
        Map<String, Integer> allProducts = storageService.getAll();
        assertTrue(allProducts.isEmpty());
    }
}
