package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        handler = new ReturnOperationHandler();
    }

    @Before
    public void setUp() {
        Storage.storage.put(new Fruit("apple"), 46);
    }

    @Test
    public void handleReturnOperation_Ok() {
        Transaction transaction = new Transaction("r",
                new Fruit("apple"), 15);
        handler.process(transaction);
        int expected = 61;
        int actual = Storage.storage.get(transaction.getFruit());
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
