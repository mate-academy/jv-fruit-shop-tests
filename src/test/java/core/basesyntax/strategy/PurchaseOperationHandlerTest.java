package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchase;

    @BeforeClass
    public static void beforeClass() throws Exception {
        purchase = new PurchaseOperationHandler();
    }

    @Test
    public void purchaseOperationHandlerIsValid_Ok() {
        Storage.getFruitsMap().put(new Fruit("apple"), 10);
        Transaction transaction = new Transaction(Transaction.Operation.PURCHASE,
                new Fruit("apple"), 5);
        Integer expected = 5;
        purchase.apply(transaction);
        assertEquals(expected, Storage.getFruitsMap().get(new Fruit("apple")));
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperationIsInvalid_NotOk() {
        purchase.apply(null);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.getFruitsMap().clear();
    }
}
