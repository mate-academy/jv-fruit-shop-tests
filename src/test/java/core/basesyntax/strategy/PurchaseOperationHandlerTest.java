package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler handler;
    private static Transaction transaction;

    @BeforeClass
    public static void beforeClass() {
        handler = new PurchaseOperationHandler();
        Storage.getFruits().put(new Fruit("apple"), 50);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getFruits().clear();
    }

    @Test
    public void testPurchase_Ok() {
        transaction = new Transaction(OperationType.PURCHASE, "apple", 30);
        int expected = 20;
        int actual = new PurchaseOperationHandler().apply(transaction);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void testNegativePurchase_Ok() {
        transaction = new Transaction(OperationType.SUPPLY, "apple", -5);
        new PurchaseOperationHandler().apply(transaction);
    }

    @Test(expected = RuntimeException.class)
    public void testRequiredAmountPurchase_NotOk() {
        transaction = new Transaction(OperationType.SUPPLY, "apple", 500);
        new PurchaseOperationHandler().apply(transaction);
    }
}
