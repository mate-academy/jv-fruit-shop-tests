package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        handler = new BalanceOperationHandler();
    }

    @Before
    public void setUp() {
        Storage.fruits.put(new Fruit("banana"), 50);
    }

    @Test
    public void balanceOperationHandler_Ok() {
        Transaction transaction = new Transaction("b", "banana", 150);
        int expected = 150;
        int actual = handler.apply(transaction);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
