package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private ReturnOperationHandler returnOperation;
    private FruitRecord fruitRecord;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperationHandler();
        Storage.storage.clear();
    }

    @Test
    public void apply_addFruitsToStorage_success() {
        fruitRecord = new FruitRecord(FruitRecord.Operation.RETURN, "apple", 50);

        returnOperation.apply(fruitRecord);

        assertEquals(50, Storage.storage.get("apple"));
    }

    @Test
    public void apply_negativeResult_throwsRuntimeException() {
        fruitRecord = new FruitRecord(FruitRecord.Operation.RETURN, "apple", -100);

        assertThrows(RuntimeException.class, () -> {
            returnOperation.apply(fruitRecord);
        });
    }

    @Test
    public void apply_nullTransaction_throwsNullPointerException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            returnOperation.apply(null);
        });
        assertEquals("Transaction cannot be null", exception.getMessage());
    }
}
