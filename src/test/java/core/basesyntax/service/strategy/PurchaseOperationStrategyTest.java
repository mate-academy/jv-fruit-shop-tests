package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationStrategyTest {
    private Storage storage = new Storage();
    private PurchaseOperationStrategy strategy = new PurchaseOperationStrategy(storage);

    @BeforeEach
    void setUp() {
        Storage storage = new Storage();
        PurchaseOperationStrategy strategy = new PurchaseOperationStrategy(storage);
    }

    @Test
    void process_checkTransactionProcessing_Ok() {
        storage.updateFruitQuantity("banana", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "banana", 5);
        strategy.process(transaction);
        assertEquals(5, storage.getFruitQuantity("banana"));
    }

    @Test
    void process_notEnoughFruits_NotOk() {
        try {
            FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "banana", 20);
            strategy.process(transaction);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("If purchase more than quantity new IllegalArgumentException should be thrown");
    }
}
