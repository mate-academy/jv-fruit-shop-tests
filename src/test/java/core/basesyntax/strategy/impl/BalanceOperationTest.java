package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationsStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final String FRUIT_NAME = "banana";
    private static final int FRUIT_QUANTITY = 120;
    private static final int NEGATIVE_FRUIT_QUANTITY = -10;
    private static OperationsStrategy balanceOperation;

    @BeforeAll
    static void beforeAll() {
        balanceOperation = new BalanceOperation();
    }

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
    }

    @Test
    void handle_balanceOperation_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_NAME, FRUIT_QUANTITY);
        balanceOperation.handle(fruitTransaction);
        Integer expected = FRUIT_QUANTITY;
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handle_negativeQuantity_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_NAME, NEGATIVE_FRUIT_QUANTITY);
        Assertions.assertThrows(RuntimeException.class, () ->
                balanceOperation.handle(fruitTransaction));
    }
}
