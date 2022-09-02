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
    private static OperationHandler operationHandler;
    private static Transaction transaction;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Before
    public void setUp() {
        transaction = new Transaction("p", new Fruit("banana"), null);
    }

    @Test
    public void applyPurchaseOperationHandler_getFruitBanana_isOk() {
        Fruit expected = new Fruit("banana");
        Fruit actual = transaction.getFruit();
        assertEquals(expected, actual);
    }

    @Test
    public void applyReturnOperationHandler_banana25_isOk() {
        Storage.storage.put(new Fruit("banana"), 10);
        transaction.setQuantity(5);
        operationHandler.apply(transaction);
        assertEquals("Expected another value: 5",
                Integer.valueOf(5),
                Storage.storage.get(new Fruit("banana")));
    }

    @Test
    public void applyPurchaseOperationHandler_apple0_isOk() {
        Storage.storage.put(new Fruit("apple"), 5);
        operationHandler.apply(new Transaction("p", new Fruit("apple"), 5));
        Integer expected = 0;
        Integer actual = Storage.storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
