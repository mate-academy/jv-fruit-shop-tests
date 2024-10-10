package core.basesyntax.service.strategy.strategyimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;
    private FruitRecord fruitRecord;

    @BeforeEach
    void setUp() {
        purchaseOperation = new PurchaseOperation();
        Storage.storage.clear();
    }

    @Test
    void apply_purchaseSuccess_decreasesQuantity() {
        Storage.storage.put("apple", 100);
        fruitRecord = new FruitRecord(FruitRecord.Operation.PURCHASE, "apple", 50);
        purchaseOperation.apply(fruitRecord);
        assertEquals(50, Storage.storage.get("apple"));
    }

    @Test
    void apply_fruitDoesNotExist_throwsIllegalArgumentException() {
        fruitRecord = new FruitRecord(FruitRecord.Operation.PURCHASE, "banana", 0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            purchaseOperation.apply(fruitRecord);
        });
        assertEquals("Fruit does not exist in storage.", exception.getMessage());

    }

    @Test
    void apply_purchaseNegativeQuantity_throwsIllegalArgumentException() {
        Storage.storage.put("apple", -10);
        FruitRecord fruitRecord = new FruitRecord(FruitRecord.Operation.PURCHASE, "apple", 10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            purchaseOperation.apply(fruitRecord);
        });
        assertEquals("Invalid quantity for purchase.", exception.getMessage());
    }

    @Test
    void apply_purchaseQuantityExceedsCurrent_throwsIllegalArgumentException() {
        Storage.storage.put("apple", 20);
        FruitRecord fruitRecord = new FruitRecord(FruitRecord.Operation.PURCHASE, "apple", 50);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            purchaseOperation.apply(fruitRecord);
        });
        assertEquals("Resulting quantity cannot be negative for fruit: "
                + fruitRecord, exception.getMessage());
    }
}
