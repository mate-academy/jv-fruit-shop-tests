package core.basesyntax.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int DEFAULT_QUANTITY = 5;
    private static final int NEGATIVE_QUANTITY = -5;
    private static final int ZERO_QUANTITY = 0;

    @BeforeEach
    void setUp() {
        operationHandler = new BalanceOperationHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void balanceHandle_addFruitZeroQuantity_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit(APPLE);
        fruitTransaction.setQuantity(ZERO_QUANTITY);

        operationHandler.handleTransaction(fruitTransaction);

        Integer actualQuantity = Storage.fruits.getOrDefault(APPLE, DEFAULT_QUANTITY);
        assertEquals(ZERO_QUANTITY, actualQuantity);
    }

    @Test
    void balanceHandle_addFruitNegativeQuantity_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit(BANANA);
        fruitTransaction.setQuantity(NEGATIVE_QUANTITY);

        assertThrows(IllegalArgumentException.class,
                () -> operationHandler.handleTransaction(fruitTransaction));
    }

    @Test
    void balanceHandle_addFruitToStorage_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit(APPLE);
        fruitTransaction.setQuantity(DEFAULT_QUANTITY);

        operationHandler.handleTransaction(fruitTransaction);

        Integer actual = Storage.fruits.getOrDefault(APPLE, DEFAULT_QUANTITY);
        assertEquals(DEFAULT_QUANTITY, actual);
    }

    @Test
    void balanceHandle_addNullTransaction_notOk() {
        FruitTransaction transaction = null;

        assertThrows(NullPointerException.class, ()
                -> operationHandler.handleTransaction(transaction));
    }
}
