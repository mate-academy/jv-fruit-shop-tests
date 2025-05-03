package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        handler = new BalanceOperationHandler();
    }

    @Test
    public void handleBalanceOperation_Ok() {
        Transaction transaction = new Transaction("b",
                new Fruit("apple"), 45);
        handler.process(transaction);
        int expected = 45;
        int actual = Storage.storage.get(transaction.getFruit());
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
