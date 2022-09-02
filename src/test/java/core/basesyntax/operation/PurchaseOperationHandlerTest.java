package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static PurchaseOperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Before
    public void setUp() {
        FruitTransaction balanceTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 200);
        new BalanceOperationHandler().apply(balanceTransaction);
    }

    @Test
    public void purchaseOperationHandler_putValidData_ok() {
        FruitTransaction purchaseTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 20);
        purchaseOperationHandler.apply(purchaseTransaction);
        Integer expected = 180;
        Integer actual = Storage.storage.get(purchaseTransaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void purchaseOperationHandler_zeroQuantity_ok() {
        FruitTransaction purchaseTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 0);
        purchaseOperationHandler.apply(purchaseTransaction);
        Integer expected = 200;
        Integer actual = Storage.storage.get(purchaseTransaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
