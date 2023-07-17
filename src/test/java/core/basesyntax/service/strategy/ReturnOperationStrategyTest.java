package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Test;

class ReturnOperationStrategyTest {

    @Test
    void process_checkTransactionProcessing_Ok() {
        Storage storage = new Storage();
        storage.updateFruitQuantity("banana", 10);
        OperationStrategy strategy = new ReturnOperationStrategy(storage);
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "banana", 5);
        strategy.process(transaction);
        assertEquals(15, storage.getFruitQuantity("banana"));
    }
}
