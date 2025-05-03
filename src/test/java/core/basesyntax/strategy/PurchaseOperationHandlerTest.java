package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
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
        Storage.fruits.put(new Fruit("banana"), 50);
    }

    @Test
    public void purchaseOperationHandler_Ok() {
        Transaction transaction = new Transaction("p", "banana", 20);
        int expected = 30;
        int actual = handler.apply(transaction);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperationHandlerWithNotEnoughFruits_NotOk() {
        handler.apply(new Transaction("p", "banana", 60));
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
