package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    public void addToStorageBalanceOperationHandler_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana", 30);
        operationHandler.operation(fruitTransaction);
        int expected = 30;
        int actual = Storage.fruitsStorage.get("banana");
        assertEquals(expected, actual);
    }
}
