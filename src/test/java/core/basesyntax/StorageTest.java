package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntex.service.impl.Storage;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    @BeforeEach
    void setUp() {
        Storage.clearStorage();
    }

    @Test
    void getQuantity_fruitNotInStorage_zeroReturned() {
        int quantity = Storage.getQuantity("apple");
        assertEquals(0, quantity, "Expected quantity to be 0 for fruit not in storage");
    }

    @Test
    void getQuantity_fruitInStorage_correctQuantityReturned() {
        Storage.updateStorage("apple", 10);
        int quantity = Storage.getQuantity("apple");
        assertEquals(10, quantity, "Expected quantity to match the updated storage amount");
    }

    @Test
    void updateStorage_addNewFruit_fruitAddedToStorage() {
        Storage.updateStorage("banana", 15);
        int quantity = Storage.getQuantity("banana");
        assertEquals(15, quantity, "Expected banana quantity to be updated to 15");
    }

    @Test
    void updateStorage_updateExistingFruitQuantity_quantityUpdated() {
        Storage.updateStorage("orange", 20);
        Storage.updateStorage("orange", 10);
        int quantity = Storage.getQuantity("orange");
        assertEquals(30, quantity, "Expected quantity to be 30 after two updates");
    }

    @Test
    void getAll_storageIsEmpty_emptyMapReturned() {
        Map<String, Integer> storage = Storage.getAll();
        assertTrue(storage.isEmpty(), "Expected storage to be empty initially");
    }

    @Test
    void getAll_storageHasFruits_correctMapReturned() {
        Storage.updateStorage("apple", 10);
        Storage.updateStorage("banana", 20);
        Map<String, Integer> storage = Storage.getAll();
        assertEquals(2, storage.size(), "Expected storage to contain two fruits");
        assertEquals(10, storage.get("apple"), "Expected apple quantity to be 10");
        assertEquals(20, storage.get("banana"), "Expected banana quantity to be 20");
    }

    @Test
    void getAll_modifyingReturnedMap_doesNotAffectOriginalStorage() {
        Storage.updateStorage("apple", 10);
        Map<String, Integer> storageCopy = Storage.getAll();
        storageCopy.put("apple", 50);
        int originalQuantity = Storage.getQuantity("apple");
        assertEquals(10, originalQuantity, "Expected original storage to remain unchanged");
    }
}
