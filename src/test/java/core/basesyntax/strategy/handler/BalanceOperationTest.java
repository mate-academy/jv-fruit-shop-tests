package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int DEFAULT_QUANTITY = 10;
    private static final int ZERO_QUANTITY = 0;
    private static final int NEGATIVE_QUANTITY = -1;
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
    void process_balanceNegativeQuantity_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, BANANA, NEGATIVE_QUANTITY
        );
        assertThrows(IllegalArgumentException.class,
                () -> operationHandler.process(fruitTransaction));
    }

    @Test
    void process_balanceZeroQuantity_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, APPLE, ZERO_QUANTITY
        );
        operationHandler.process(fruitTransaction);
        int actual = Storage.getFruits().getOrDefault(APPLE, ZERO_QUANTITY);
        assertEquals(actual, ZERO_QUANTITY);
    }

    @Test
    void process_balanceDefaultQuantity_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, BANANA, DEFAULT_QUANTITY
        );
        operationHandler.process(fruitTransaction);
        int actual = Storage.getFruits().getOrDefault(BANANA, DEFAULT_QUANTITY);
        assertEquals(actual, DEFAULT_QUANTITY);
    }
}
