package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionLine;
import org.junit.Test;

public class BalansOperationHandlerTest {
    private static final OperationHandler OPERATION_HANDLER = new BalansOperationHandler();
    private static final TransactionLine TRANSACTION_LINE_BALANCE =
            new TransactionLine("b", "banana", 50);
    private static final Fruit fruit = new Fruit("banana");

    @Test
    public void operation_Ok() {
        OPERATION_HANDLER.operation(TRANSACTION_LINE_BALANCE);
        int expected = 50;
        int actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test (expected = AssertionError.class)
    public void operation_NotOk() {
        OPERATION_HANDLER.operation(TRANSACTION_LINE_BALANCE);
        int expectedNotOk = 1000;
        int actual = Storage.storage.get(fruit);
        assertEquals(expectedNotOk, actual);
    }
}
