package core.basesyntax.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationStrategyTest {
    private static Storage storage;
    private static BalanceOperationStrategy strategy;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage();
        strategy = new BalanceOperationStrategy(storage);
    }

    @Test
    void process_checkTransactionProcessing_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "banana", 20);
        strategy.process(transaction);
        Assertions.assertEquals(20, storage.getFruitQuantity("banana"));
    }
}
