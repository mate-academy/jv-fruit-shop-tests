package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseHandler;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        purchaseHandler = new PurchaseOperationHandler();
        fruit = new Fruit("apple");
    }

    @Test
    public void purchaseOperationHandlerIsValid_Ok() {
        Storage.getFruitsMap().put(fruit, 10);
        Transaction transaction = new Transaction(Transaction.Operation.PURCHASE,
                fruit, 5);
        Integer expected = 5;
        purchaseHandler.apply(transaction);
        assertEquals(expected, Storage.getFruitsMap().get(fruit));
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperationIsInvalid_NotOk() {
        purchaseHandler.apply(null);
    }

    @After
    public void tearDown() {
        Storage.getFruitsMap().clear();
    }
}
