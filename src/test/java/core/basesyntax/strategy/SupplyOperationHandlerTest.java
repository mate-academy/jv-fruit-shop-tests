package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        handler = new SupplyOperationHandler();
    }

    @Before
    public void setUp() {
        Storage.storage.put(new Fruit("banana"), 120);
    }

    @Test
    public void handleSupplyOperation_Ok() {
        Transaction transaction = new Transaction("s",
                new Fruit("banana"), 300);
        handler.process(transaction);
        int expected = 420;
        int actual = Storage.storage.get(transaction.getFruit());
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
