package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageServiceImpTest {
    private static final int TYPICAL_QUANTITY_FIFTEEN = 15;
    private static final int TYPICAL_QUANTITY_TEN = 10;
    private static final int NEGATIVE_TYPICAL_QUANTITY_TEN = -10;
    private static final int TYPICAL_QUANTITY_FIVE = 5;
    private static final int TYPICAL_QUANTITY_ZERO = 0;
    private StorageServiceImp storageService;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImp();
        storageService.clear();
    }

    @Test
    void addZeroQuantity_doesNotCreateEntry() {
        storageService.add("banana", TYPICAL_QUANTITY_ZERO);
        int quantity = storageService.getQuantity("banana");
        assertEquals(TYPICAL_QUANTITY_ZERO, quantity);
    }

    @Test
    void addFruit_newFruit_storageContains() {
        storageService.add("apple", TYPICAL_QUANTITY_TEN);
        int getQuantity = storageService.getQuantity("apple");
        assertEquals(TYPICAL_QUANTITY_TEN, getQuantity, "Error, quantity expected: "
                + TYPICAL_QUANTITY_TEN + " but was: " + getQuantity);
    }

    @Test
    void addFruits_newFruits_storageContains() {
        storageService.add("banana", TYPICAL_QUANTITY_TEN);
        storageService.add("apple", TYPICAL_QUANTITY_FIVE);
        int quantityBanana = storageService.getQuantity("banana");
        int quantityApple = storageService.getQuantity("apple");
        assertEquals(TYPICAL_QUANTITY_TEN, quantityBanana, "Error, quantityBanana expected: "
                + TYPICAL_QUANTITY_TEN + " but was: " + quantityBanana);
        assertEquals(TYPICAL_QUANTITY_FIVE, quantityApple, "Error, quantityApple expected: "
                + TYPICAL_QUANTITY_FIVE + " but was: " + quantityApple);
    }

    @Test
    void addSameFruits_newFruits_storageContains() {
        storageService.add("banana", TYPICAL_QUANTITY_TEN);
        storageService.add("banana", TYPICAL_QUANTITY_FIVE);
        int quantityBanana = storageService.getQuantity("banana");
        assertEquals(TYPICAL_QUANTITY_FIFTEEN, quantityBanana, "Error, quantityBanana expected: "
                + TYPICAL_QUANTITY_FIFTEEN + " but was: " + quantityBanana);
    }

    @Test
    void addMinusFruit_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
                        storageService.add("banana", NEGATIVE_TYPICAL_QUANTITY_TEN),
                "Expected IllegalArgumentException "
                        + "when adding negative quantity"
        );
    }

    @Test
    void thisFruitIsAbsent_IsOk() {
        int getQuantity = storageService.getQuantity("mango");
        assertEquals(0, getQuantity, "This fruit can't be here");
    }

    @Test
    void removeFruit_existingFruit_quantityDecrease() {
        storageService.add("banana", TYPICAL_QUANTITY_TEN);
        storageService.remove("banana", TYPICAL_QUANTITY_FIVE);
        int quantity = storageService.getQuantity("banana");
        assertEquals(TYPICAL_QUANTITY_FIVE, quantity, "Error: result must be: "
                + TYPICAL_QUANTITY_FIVE);
    }

    @Test
    void removeFruit_existingFruit_toNull() {
        storageService.add("banana", TYPICAL_QUANTITY_FIVE);
        storageService.remove("banana", TYPICAL_QUANTITY_FIVE);
        assertFalse(storageService.getAll().containsKey("banana"));
    }

    @Test
    void removeFruit_notExistingFruit_quantityDecrease() {
        assertThrows(RuntimeException.class, () -> storageService
                .remove("banana", TYPICAL_QUANTITY_FIVE), "Expected RuntimeException "
                + "when trying to remove a non-existing fruit");
    }

    @Test
    void addRemoveFruit_existingFruit_badQuantityDecrease() {
        storageService.add("banana", TYPICAL_QUANTITY_FIVE);
        assertThrows(RuntimeException.class, () -> storageService
                .remove("banana", TYPICAL_QUANTITY_TEN),"Expected "
                + "RuntimeException when trying to remove more than available quantity"
        );
    }

    @Test
    void removeFruit_existingFruit_negativeQuantityDecrease() {
        storageService.add("banana", TYPICAL_QUANTITY_TEN);
        assertThrows(IllegalArgumentException.class, () -> storageService
                .remove("banana", NEGATIVE_TYPICAL_QUANTITY_TEN), "Expected "
                + "IllegalArgumentException "
                + "when trying to remove a existing fruit with negative Decrease");
    }

    @Test
    void removeZeroFruit_notOk() {
        assertThrows(RuntimeException.class,
                () -> storageService.remove(null, TYPICAL_QUANTITY_FIVE));
    }

    @Test
    void clear_isOk() {
        storageService.add("banana", TYPICAL_QUANTITY_TEN);
        storageService.add("apple", TYPICAL_QUANTITY_FIVE);
        storageService.clear();
        assertTrue(storageService.getAll().isEmpty(),
                "Expected storage to be empty after clear");
    }

    @Test
    void copyOfFruits_cantBeChangedBy_getAll() {
        storageService.add("banana", TYPICAL_QUANTITY_TEN);
        storageService.add("apple", TYPICAL_QUANTITY_FIVE);
        Map<String, Integer> all = storageService.getAll();
        assertTrue(all.containsKey("banana"), "Expected 'banana' to be present");
        assertTrue(all.containsKey("apple"), "Expected 'apple' to be present");
        assertEquals(TYPICAL_QUANTITY_TEN, all.get("banana"));
        assertEquals(TYPICAL_QUANTITY_FIVE, all.get("apple"));
        assertThrows(UnsupportedOperationException.class, () ->
                all.put("banana", TYPICAL_QUANTITY_FIVE));
    }
}
