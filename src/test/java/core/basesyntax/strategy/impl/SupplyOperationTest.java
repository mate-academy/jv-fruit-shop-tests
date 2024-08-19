package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static final String APPLE = "apple";
    private static final int DEFAULT_QUANTITY = 10;
    private static final int ZERO_QUANTITY = 0;
    private static final int NEGATIVE_QUANTITY = -1;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new ReturnOperation();
    }

    @AfterEach
    void afterEach() {
        Storage.clear();
    }

    @Test
    void apply_supplyDefQuantity_ok() {
        Storage.getFruits().put(APPLE, DEFAULT_QUANTITY);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, APPLE,DEFAULT_QUANTITY);
        operationHandler.apply(fruitTransaction);
        int actual = Storage.getFruits().get(APPLE);
        assertEquals(actual, DEFAULT_QUANTITY + DEFAULT_QUANTITY);
    }

    @Test
    void apply_supplyNegQuantity_notOK() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, APPLE, NEGATIVE_QUANTITY);
        assertThrows(IllegalArgumentException.class, () -> operationHandler
                .apply(fruitTransaction));
    }

    @Test
    void apply_supplyZeroProducts_notOk() {
        Storage.getFruits().put(APPLE, DEFAULT_QUANTITY);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, APPLE, ZERO_QUANTITY);
        assertThrows(IllegalArgumentException.class, () -> operationHandler
                .apply(fruitTransaction));
    }
}
