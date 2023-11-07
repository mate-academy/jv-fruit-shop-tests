package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.Storage;
import core.basesyntax.transaction.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final int INITIAL_FRUIT_QUANTITY = 100;
    private static final int RETURN_QUANTITY = 20;
    private static final String BANANA = "banana";
    private static final int INVALID_FRUIT_QUANTITY = -10;
    private static FruitTransaction validFruitTransaction;
    private static FruitTransaction invalidFruitTransaction;
    private static ReturnOperationHandler returnOperationHandler;

    @BeforeAll
    static void beforeAll() {
        returnOperationHandler = new ReturnOperationHandler();

        validFruitTransaction = new FruitTransaction();
        validFruitTransaction.setFruit(APPLE);
        validFruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        validFruitTransaction.setQuantity(RETURN_QUANTITY);

        invalidFruitTransaction = new FruitTransaction();
        invalidFruitTransaction.setFruit(BANANA);
        invalidFruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        invalidFruitTransaction.setQuantity(INVALID_FRUIT_QUANTITY);
    }

    @BeforeEach
    void setUp() {
        Storage.getFruitBalance().clear();
        Storage.getFruitBalance().put(APPLE, INITIAL_FRUIT_QUANTITY);
        Storage.getFruitBalance().put(BANANA, INITIAL_FRUIT_QUANTITY);
    }

    @Test
    void handle_Valid_Transaction_Ok() {
        returnOperationHandler.handleOperation(validFruitTransaction);
        assertEquals(INITIAL_FRUIT_QUANTITY + RETURN_QUANTITY,
                Storage.getFruitBalance().get(APPLE));
    }

    @Test
    void handle_Invalid_Transaction_NotOk() {
        assertThrows(RuntimeException.class,
                () -> returnOperationHandler.handleOperation(invalidFruitTransaction));
    }
}
