package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int DEFAULT_QUANTITY = 10;
    private static final int NEGATIVE_QUANTITY = -1;
    private static final int ZERO_QUANTITY = 0;
    private static OperationHandler handler;

    @BeforeAll
    static void beforeAll() {
        handler = new BalanceOperationHandler();
    }

    @AfterEach
    void afterEach() {
        Storage.fruits.clear();
    }

    @Test
    void balanceHandle_negativeQuantity_notOK() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, BANANA, NEGATIVE_QUANTITY);
        assertThrows(IllegalArgumentException.class, () -> handler.process(fruitTransaction));
    }

    @Test
    void balanceHandle_zeroQuantity_ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, ZERO_QUANTITY);
        handler.process(fruitTransaction);
        Integer actualQuantity = Storage.fruits.getOrDefault(APPLE, ZERO_QUANTITY);
        assertEquals(ZERO_QUANTITY, actualQuantity);
    }

    @Test
    void balanceHandle_normalQuantity_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, APPLE, DEFAULT_QUANTITY);
        handler.process(fruitTransaction);
        Integer actual = Storage.fruits.getOrDefault(APPLE, DEFAULT_QUANTITY);
        assertEquals(DEFAULT_QUANTITY, actual);
    }

    @Test
    void balanceHandle_nullTransaction_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, BANANA, NEGATIVE_QUANTITY);
        assertThrows(IllegalArgumentException.class, () -> handler.process(transaction));
    }
}
