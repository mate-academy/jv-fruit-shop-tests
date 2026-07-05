package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private OperationHandler purchaseOperation;

    @BeforeEach
    void setUp() {
        Storage.clear();
        purchaseOperation = new PurchaseOperation();
    }

    @Test
    void process_validTransaction_Ok() {
        Storage.put("banana", 100);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "banana", 30);
        purchaseOperation.process(transaction);
        assertEquals(70, Storage.get("banana"));
    }

    @Test
    void process_notEnoughFruit_ThrowsException() {
        Storage.put("banana", 100);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "banana", 150);
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.process(transaction));
    }
}
