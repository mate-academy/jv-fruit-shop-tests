package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseHandler;

    @BeforeClass
    public static void beforeClass() {
        purchaseHandler = new PurchaseOperationHandler();
    }

    @Test
    public void purchaseIsValid_Ok() {
        Transaction transaction = new Transaction(Transaction.Operation
                .PURCHASE, new Fruit("banana"), 6);
        Storage.getStorage().put(new Fruit("banana"), 10);
        purchaseHandler.apply(transaction);
        Integer expected = 4;
        Integer actual = Storage.getStorage().get(new Fruit("banana"));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseIsValid_NotOk() {
        purchaseHandler.apply(null);
    }
}
