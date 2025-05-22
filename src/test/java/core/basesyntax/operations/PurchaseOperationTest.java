package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        PurchaseOperation purchaseOperation = new PurchaseOperation();
        Storage.storage.put("banana", 0);
    }

    @Test
    void getCalculation_invalidFruit_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            purchaseOperation.getCalculation(new FruitTransaction(
                    FruitTransaction.Operation.PURCHASE,"invalidFruit",6));
        });
    }

    @Test
    void getCalculation_negativeValue_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            purchaseOperation.getCalculation(new FruitTransaction(
                    FruitTransaction.Operation.PURCHASE,"banana",5));
        });
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
