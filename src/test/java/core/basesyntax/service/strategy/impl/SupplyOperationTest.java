package core.basesyntax.service.strategy.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private SupplyOperationHandler supplyOperation;
    private FruitRecord fruitRecord;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperationHandler();
        Storage.storage.clear();
    }

    @Test
    void apply_addNewFruitToStorage_success() {
        fruitRecord = new FruitRecord(FruitRecord.Operation.SUPPLY, "carrot", 10);
        supplyOperation.apply(fruitRecord);
        assertEquals(10, Storage.storage.get("carrot"));
    }

    @Test
    void apply_addQuantityToExistingFruit_success() {
        Storage.storage.put("apple", 50);
        fruitRecord = new FruitRecord(FruitRecord.Operation.SUPPLY, "apple", 30);
        supplyOperation.apply(fruitRecord);
        assertEquals(80, Storage.storage.get("apple"));
    }

    @Test
    void apply_addZeroQuantity_success() {
        Storage.storage.put("orange", 10);
        fruitRecord = new FruitRecord(FruitRecord.Operation.SUPPLY, "orange", 0);

        supplyOperation.apply(fruitRecord);

        assertEquals(10, Storage.storage.get("orange"));
    }

    @Test
    void apply_nullTransaction_throwsNullPointerException() {
        FruitRecord nullTransaction = null;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            supplyOperation.apply(null);
        });

        assertEquals("Transaction cannot be null", exception.getMessage());
    }

    @Test
    void apply_negativeSupply_throwsIllegalArgumentException() {
        fruitRecord = new FruitRecord(FruitRecord.Operation.SUPPLY, "apple", -50);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            supplyOperation.apply(fruitRecord);
        });

        assertEquals("Supply quantity cannot be negative.", exception.getMessage());
    }
}
