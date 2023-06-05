package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationsStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final String BANANA = "banana";
    private static OperationsStrategy balanceOperation;

    @BeforeAll
    static void beforeAll() {
        balanceOperation = new BalanceOperation();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void handle_balanceOperation_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                BALANCE, BANANA, 120);
        balanceOperation.handle(fruitTransaction);
        Integer expected = 120;
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handle_negativeQuantity_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                BALANCE, BANANA, -10);
        Assertions.assertThrows(RuntimeException.class, () ->
                balanceOperation.handle(fruitTransaction));
    }
}
