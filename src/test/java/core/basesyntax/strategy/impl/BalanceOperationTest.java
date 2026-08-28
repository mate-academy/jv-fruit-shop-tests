package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private FruitStorage storage;
    private OperationHandler balanceOperation;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        balanceOperation = new BalanceOperation(storage);
    }

    @Test
    void apply_validBalanceTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana",
                20
        );

        balanceOperation.apply(transaction);
        int actual = storage.getQuantity("banana");
        assertEquals(20, actual);
    }
}
