package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        Storage.fruitsStorage.put("banana", 20);
        operationHandler = new ReturnOperationHandler();
    }

    @Test
    public void addToStorageReturnOperationHandler_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 10);
        operationHandler.operation(fruitTransaction);
        int expected = 30;
        int actual = Storage.fruitsStorage.get("banana");
        assertEquals(expected, actual);
    }
}
