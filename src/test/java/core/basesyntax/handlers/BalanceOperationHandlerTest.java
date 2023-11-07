package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.Storage;
import core.basesyntax.transaction.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final int FRUIT_QUANTITY = 100;
    private static final String BANANA = "banana";
    private static final int INVALID_FRUIT_QUANTITY = -10;
    private static FruitTransaction validFruitTransaction;
    private static FruitTransaction invalidFruitTransaction;
    private static BalanceOperationHandler balanceOperationHandler;

    @BeforeAll
    static void beforeAll() {
        balanceOperationHandler = new BalanceOperationHandler();

        validFruitTransaction = new FruitTransaction();
        validFruitTransaction.setFruit(APPLE);
        validFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        validFruitTransaction.setQuantity(FRUIT_QUANTITY);

        invalidFruitTransaction = new FruitTransaction();
        invalidFruitTransaction.setFruit(BANANA);
        invalidFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        invalidFruitTransaction.setQuantity(INVALID_FRUIT_QUANTITY);
    }

    @AfterEach
    void afterEach() {
        Storage.getFruitBalance().clear();
    }

    @Test
    void handle_Valid_Operation_Ok() {
        balanceOperationHandler.handleOperation(validFruitTransaction);
        assertEquals(FRUIT_QUANTITY, Storage.getFruitBalance().get(APPLE));
    }

    @Test
    void handle_Invalid_Operation_NotOk() {
        assertThrows(RuntimeException.class,
                () -> balanceOperationHandler.handleOperation(invalidFruitTransaction));
    }
}
