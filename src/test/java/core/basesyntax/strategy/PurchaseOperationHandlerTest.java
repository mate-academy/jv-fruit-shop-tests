package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
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
    public void before() {
        Storage.getFruits().put(new Fruit("apple"), 50);
    }

    @After
    public void after() {
        Storage.getFruits().clear();
    }

    @Test
    public void testPurchase_Ok() {
        Transaction transaction = new Transaction(OperationType.PURCHASE, "apple", 10);
        int expected = 40;
        int actual = handler.apply(transaction);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void testNegativePurchase_Ok() {
        Transaction transaction = new Transaction(OperationType.PURCHASE, "apple", -5);
        handler.apply(transaction);
    }

    @Test(expected = RuntimeException.class)
    public void testRequiredAmountPurchase_NotOk() {
        Transaction transaction = new Transaction(OperationType.PURCHASE, "apple", 500);
        handler.apply(transaction);
    }
}
