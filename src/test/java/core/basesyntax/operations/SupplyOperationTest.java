package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperation();
    }

    @Test
    void getCalculation_invalidFruit_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            supplyOperation.getCalculation(new FruitTransaction(
                    FruitTransaction.Operation.SUPPLY,"invalidFruit",6));
        });
    }

    @Test
    void getCalculation_addData_Ok() {
        Storage.storage.clear();
        Storage.storage.put("apple", 0);
        supplyOperation.getCalculation(new FruitTransaction(
                FruitTransaction.Operation.RETURN,"apple",5));
        assertEquals(5, Storage.storage.get("apple"));
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
