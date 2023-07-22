package core.basesyntax.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BalanceOperationStrategyTest {

    @Test
    void process_checkTransactionProcessing_Ok() {
        Storage storage = new Storage();
        BalanceOperationStrategy strategy = new BalanceOperationStrategy(storage);
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "banana", 20);
        strategy.process(transaction);
        Assertions.assertEquals(20, storage.getFruitQuantity("banana"));
    }
}
