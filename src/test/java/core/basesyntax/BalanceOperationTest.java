package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static Storage storage;
    private static OperationHandler balanceOperation;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        balanceOperation = new BalanceOperation();
    }

    @Test
    void handle_balanceOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE,
                "banana", 50);
        balanceOperation.handle(transaction, storage);
        Assertions.assertEquals(50, storage.getInventory().get("banana"));
    }
}
