package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.AfterEach;
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
    }

    @AfterEach
    void tearDown() {
        storageService.clear();
    }

    @Test
    void addZeroQuantity_doesNotCreateEntry() {
        storageService.add("banana", TYPICAL_QUANTITY_ZERO);
        assertFalse(storageService.getAll().containsKey("banana"));
    }

    @Test
    void addFruit_newFruit_storageContains() {
        storageService.add("apple", TYPICAL_QUANTITY_TEN);
        int getQuantity = storageService.getQuantity("apple");
        assertEquals(TYPICAL_QUANTITY_TEN, getQuantity);
    }

    @Test
    void addFruits_newFruits_storageContains() {
        storageService.add("banana", TYPICAL_QUANTITY_TEN);
        storageService.add("apple", TYPICAL_QUANTITY_FIVE);
        assertEquals(TYPICAL_QUANTITY_TEN, storageService.getQuantity("banana"));
        assertEquals(TYPICAL_QUANTITY_FIVE, storageService.getQuantity("apple"));
    }

    @Test
    void addSameFruits_newFruits_storageContains() {
        storageService.add("banana", TYPICAL_QUANTITY_TEN);
        storageService.add("banana", TYPICAL_QUANTITY_FIVE);
        assertEquals(TYPICAL_QUANTITY_FIFTEEN, storageService.getQuantity("banana"));
    }

    @Test
    void addMinusFruit_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
                storageService.add("banana", NEGATIVE_TYPICAL_QUANTITY_TEN));
    }

    @Test
    void thisFruitIsAbsent_IsOk() {
        assertEquals(0, storageService.getQuantity("mango"));
    }

    @Test
    void removeFruit_existingFruit_quantityDecrease() {
        storageService.add("banana", TYPICAL_QUANTITY_TEN);
        storageService.remove("banana", TYPICAL_QUANTITY_FIVE);
        assertEquals(TYPICAL_QUANTITY_FIVE, storageService.getQuantity("banana"));
    }

    @Test
    void removeFruit_existingFruit_toNull() {
        storageService.add("banana", TYPICAL_QUANTITY_FIVE);
        storageService.remove("banana", TYPICAL_QUANTITY_FIVE);
        assertFalse(storageService.getAll().containsKey("banana"));
    }

    @Test
    void removeFruit_notExistingFruit_quantityDecrease() {
        assertThrows(RuntimeException.class, () ->
                storageService.remove("banana", TYPICAL_QUANTITY_FIVE));
    }

    @Test
    void addRemoveFruit_existingFruit_badQuantityDecrease() {
        storageService.add("banana", TYPICAL_QUANTITY_FIVE);
        assertThrows(RuntimeException.class, () ->
                storageService.remove("banana", TYPICAL_QUANTITY_TEN));
    }

    @Test
    void removeFruit_existingFruit_negativeQuantityDecrease() {
        storageService.add("banana", TYPICAL_QUANTITY_TEN);
        assertThrows(IllegalArgumentException.class, () ->
                storageService.remove("banana", NEGATIVE_TYPICAL_QUANTITY_TEN));
    }

    @Test
    void removeZeroFruit_notOk() {
        assertThrows(RuntimeException.class, () ->
                storageService.remove(null, TYPICAL_QUANTITY_FIVE));
    }

    @Test
    void clear_isOk() {
        storageService.add("banana", TYPICAL_QUANTITY_TEN);
        storageService.add("apple", TYPICAL_QUANTITY_FIVE);
        storageService.clear();
        assertTrue(storageService.getAll().isEmpty());
    }

    @Test
    void copyOfFruits_cantBeChangedBy_getAll() {
        storageService.add("banana", TYPICAL_QUANTITY_TEN);
        storageService.add("apple", TYPICAL_QUANTITY_FIVE);
        Map<String, Integer> all = storageService.getAll();
        assertTrue(all.containsKey("banana"));
        assertTrue(all.containsKey("apple"));
        assertEquals(TYPICAL_QUANTITY_TEN, all.get("banana"));
        assertEquals(TYPICAL_QUANTITY_FIVE, all.get("apple"));
        assertThrows(UnsupportedOperationException.class, () ->
                all.put("banana", TYPICAL_QUANTITY_FIVE));
    }
}
