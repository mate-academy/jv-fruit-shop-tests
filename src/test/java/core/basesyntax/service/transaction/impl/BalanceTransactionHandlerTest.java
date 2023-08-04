package core.basesyntax.service.transaction.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitShopOperation;
import core.basesyntax.model.FruitTransaction;
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
                FruitShopOperation.BALANCE, "apple", 60);
        FruitTransaction transactionApple = new FruitTransaction(
                FruitShopOperation.BALANCE, "banana", 60);
        Map<String, Integer> expected = new HashMap<>(
                Map.of("apple", 60, "banana", 60));
        balanceTransactionHandler.executeTransaction(transactionPineapple);
        balanceTransactionHandler.executeTransaction(transactionApple);
        Assertions.assertEquals(expected, Storage.getAll(),
                "Balance operation doesn't work correctly: ");
        Storage.clear();
    }
}
