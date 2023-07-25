package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationStrategyTest {
    private static Storage storage;
    private static ReturnOperationStrategy strategy;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        strategy = new ReturnOperationStrategy(storage);
    }

    @Test
    void process_checkTransactionProcessing_Ok() {
        storage.updateFruitQuantity("banana", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "banana", 5);
        strategy.process(transaction);
        assertEquals(15, storage.getFruitQuantity("banana"));
    }
}
