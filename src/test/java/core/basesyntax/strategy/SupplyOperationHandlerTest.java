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
    private static OperationHandler operationHandler;
    private static Transaction transaction;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new SupplyOperationHandler();
    }

    @Before
    public void setUp() {
        transaction = new Transaction("s", new Fruit("banana"), null);
    }

    @Test
    public void applySupplyOperationHandler_currentQuantity0_isOk() {
        Storage.storage.put(new Fruit("apple"), 0);
        Integer expected = 0;
        Integer actual = Storage.storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test
    public void applySupplyOperationHandler_banana25_isOk() {
        Storage.storage.put(new Fruit("banana"), 20);
        transaction.setQuantity(5);
        operationHandler.apply(transaction);
        assertEquals("Expected another value: 25",
                Integer.valueOf(25),
                Storage.storage.get(new Fruit("banana")));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
