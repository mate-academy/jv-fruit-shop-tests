package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReturnOperationHandlerTest {
    private static final String FRUIT_NAME = "apple";
    private static final int INITIAL_QUANTITY = 20;
    private static final int RETURN_QUANTITY = 10;
    private static final int EMPTY_VALUE = 0;

    private OperationHandler returnOperationHandler;

    @BeforeEach
    void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
        Storage.storage.clear();
    }

    @Test
    void handle_validTransaction_increasesStorage() {
        Storage.storage.put(FRUIT_NAME, INITIAL_QUANTITY);
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, FRUIT_NAME, RETURN_QUANTITY);
        returnOperationHandler.handle(fruitTransaction);
        int expectedQuantity = INITIAL_QUANTITY + RETURN_QUANTITY;
        assertEquals(expectedQuantity, Storage.storage.get(FRUIT_NAME));
    }

    @Test
    void handle_invalidTransaction_throwsRuntimeException() {
        FruitTransaction fruitTransactionWithNullFruit = new FruitTransaction(FruitTransaction.Operation.RETURN, null, RETURN_QUANTITY);
        assertThrows(RuntimeException.class, () -> returnOperationHandler.handle(fruitTransactionWithNullFruit));
        FruitTransaction fruitTransactionWithNullOperation = new FruitTransaction(null, FRUIT_NAME, RETURN_QUANTITY);
        assertThrows(RuntimeException.class, () -> returnOperationHandler.handle(fruitTransactionWithNullOperation));
    }

    @Test
    void handle_noInitialQuantity_increasesStorage() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, FRUIT_NAME, RETURN_QUANTITY);
        returnOperationHandler.handle(fruitTransaction);
        assertEquals(RETURN_QUANTITY, Storage.storage.getOrDefault(FRUIT_NAME, EMPTY_VALUE));
    }
}