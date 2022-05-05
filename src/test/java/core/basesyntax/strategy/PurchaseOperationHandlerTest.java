package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        handler = new PurchaseOperationHandler();
    }

    @Before
    public void setUp() {
        Storage.storage.put(new Fruit("banana"), 18);
    }

    @Test
    public void handlePurchaseOperation_Ok() {
        Transaction transaction = new Transaction("p",
                new Fruit("banana"), 18);
        handler.process(transaction);
        int expected = 0;
        int actual = Storage.storage.get(transaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handlePurchaseOperation_notEnough_notOk() {
        Transaction transaction = new Transaction("p",
                new Fruit("banana"), 37);
        handler.process(transaction);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
