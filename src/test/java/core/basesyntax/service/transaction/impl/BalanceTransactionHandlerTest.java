package core.basesyntax.service.transaction.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.transaction.TransactionHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceTransactionHandlerTest {
    private TransactionHandler balanceTransactionHandler;

    @BeforeEach
    void setUp() {
        balanceTransactionHandler = new BalanceTransactionHandler();
    }

    @Test
    void executeReturnTransaction_OK() {
        FruitTransaction transactionPineapple = new FruitTransaction(
                Operation.BALANCE, Fruit.PINEAPPLE, 60);
        FruitTransaction transactionApple = new FruitTransaction(
                Operation.BALANCE, Fruit.APPLE, 60);
        Map<Fruit, Integer> expected = new HashMap<>(
                Map.of(Fruit.PINEAPPLE, 60, Fruit.APPLE, 60));
        balanceTransactionHandler.executeTransaction(transactionPineapple);
        balanceTransactionHandler.executeTransaction(transactionApple);
        Assertions.assertEquals(expected, Storage.getAll(),
                "Balance operation doesn't work correctly: ");
        Storage.clear();
    }
}
