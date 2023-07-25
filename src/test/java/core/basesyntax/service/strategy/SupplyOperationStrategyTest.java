package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationStrategyTest {
    private static Storage storage;
    private static SupplyOperationStrategy strategy;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        strategy = new SupplyOperationStrategy(storage);
    }

    @Test
    void process_checkTransactionProcessing_Ok() {
        storage.updateFruitQuantity("banana", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "banana", 20);
        strategy.process(transaction);
        assertEquals(30, storage.getFruitQuantity("banana"));
    }
}
