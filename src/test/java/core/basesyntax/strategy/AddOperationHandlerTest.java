package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionLine;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static final OperationHandler OPERATION_HANDLER = new AddOperationHandler();
    private static final TransactionLine TRANSACTION_LINE_BALANCE =
            new TransactionLine("b", "banana", 50);
    private static final TransactionLine TRANSACTION_LINE_SUPPLY =
            new TransactionLine("s", "banana", 100);
    private static final Fruit FRUIT = new Fruit("banana");

    @Test
    public void operation_Ok() {
        Storage.storage.put(FRUIT, TRANSACTION_LINE_BALANCE.getQuantity());
        OPERATION_HANDLER.operation(TRANSACTION_LINE_SUPPLY);
        int expected = 150;
        int actual = Storage.storage.get(FRUIT);
        assertEquals(expected, actual);
    }
}
