package core.basesyntax.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperation;
    private static FruitTransaction transaction;

    @BeforeClass
    public static void before() {
        purchaseOperation = new PurchaseOperationHandler();
        transaction = new FruitTransaction(Operation.PURCHASE,"banana", 50);
    }

    @Test
    public void validPurchaseOperation_Ok() {
        Storage.fruits.put("banana", 70);
        Integer expected = Storage.fruits.get(transaction.getFruit()) - transaction.getQuantity();
        purchaseOperation.handle(transaction);
        Integer actual = Storage.fruits.get(transaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void noValidPurchaseOperation_NotOk() {
        Storage.fruits.put("banana", 70);
        Integer expected = Storage.fruits.get(transaction.getFruit()) + transaction.getQuantity();
        purchaseOperation.handle(transaction);
        Integer actual = Storage.fruits.get(transaction.getFruit());
        Assert.assertNotEquals(expected, actual);
    }

    @After
    public void cleanAfter() {
        Storage.fruits.clear();
    }
}
