package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final String FRUIT_NAME = "apple";
    private static final int INITIAL_QUANTITY = 20;
    private static final int RETURN_QUANTITY = 10;
    private static final int EMPTY_VALUE = 0;

    private OperationHandler returnOperationHandler;

    @Before
    public void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
        Storage.storage.clear();
    }

    @Test
    public void handle_validTransaction_ok() {
        Storage.storage.put(FRUIT_NAME, INITIAL_QUANTITY);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        FRUIT_NAME,
                        RETURN_QUANTITY);
        returnOperationHandler.handle(fruitTransaction);
        int expectedQuantity = INITIAL_QUANTITY + RETURN_QUANTITY;
        assertEquals(expectedQuantity, (int) Storage.storage.get(FRUIT_NAME));
    }

    @Test(expected = RuntimeException.class)
    public void handle_invalidTransaction_notOk() {
        FruitTransaction fruitTransactionWithNullFruit =
                new FruitTransaction(FruitTransaction.Operation.RETURN, null, RETURN_QUANTITY);
        returnOperationHandler.handle(fruitTransactionWithNullFruit);
    }

    @Test(expected = RuntimeException.class)
    public void handle_invalidTransactionWithNullOperation_notOk() {
        FruitTransaction fruitTransactionWithNullOperation =
                new FruitTransaction(null, FRUIT_NAME, RETURN_QUANTITY);
        returnOperationHandler.handle(fruitTransactionWithNullOperation);
    }

    @Test
    public void handle_noInitialQuantity_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        FRUIT_NAME,
                        RETURN_QUANTITY);
        returnOperationHandler.handle(fruitTransaction);
        assertEquals(RETURN_QUANTITY, (int) Storage.storage.getOrDefault(FRUIT_NAME, EMPTY_VALUE));
    }
}
