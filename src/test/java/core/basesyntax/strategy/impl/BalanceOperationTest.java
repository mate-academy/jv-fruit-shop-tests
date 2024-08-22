package core.basesyntax.strategy.impl;

import static core.basesyntax.TestConstants.APPLE;
import static core.basesyntax.TestConstants.BANANA;
import static core.basesyntax.TestConstants.DEFAULT_QUANTITY;
import static core.basesyntax.TestConstants.NEGATIVE_QUANTITY;
import static core.basesyntax.TestConstants.ZERO_QUANTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceOperation();
    }

    @AfterEach
    void afterEach() {
        Storage.clear();
    }

    @Test
    void apply_balanceDefQuantity_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, APPLE, DEFAULT_QUANTITY);
        operationHandler.apply(fruitTransaction);
        int actual = Storage.getFruits().get(APPLE);
        assertEquals(actual, DEFAULT_QUANTITY);
    }

    @Test
    void apply_balanceNegQuantity_notOK() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, BANANA, NEGATIVE_QUANTITY);
        assertThrows(IllegalArgumentException.class, () -> operationHandler
                .apply(fruitTransaction));
    }

    @Test
    void apply_balanceZeroProducts_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, APPLE,ZERO_QUANTITY);
        operationHandler.apply(fruitTransaction);
        int actual = Storage.getFruits().get(APPLE);
        assertEquals(actual, ZERO_QUANTITY);
    }

}
