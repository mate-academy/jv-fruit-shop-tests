package core.basesyntax.strategy.operation.impl;

import core.basesyntax.db.Storage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationImplTest {
    private static PurchaseOperationImpl purchaseOperation;
    private static String BANANA = "banana";
    private static int BANANA_VALUE = 20;

    @BeforeClass
    public static void beforeClass() {
        purchaseOperation = new PurchaseOperationImpl();
    }

    @Test
    public void applyOperation_correctData_ok() {
        Storage.FRUIT_STORAGE.put(BANANA, BANANA_VALUE);
        int expected = BANANA_VALUE;
        int actual = purchaseOperation.getBalanceFromFruitName(BANANA);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void applyOperation_uncorrectedData_notOk() {
        Storage.FRUIT_STORAGE.put(BANANA, BANANA_VALUE);
        int oldValue = new ReturnOperationImpl().getBalanceFromFruitName(BANANA);
        int newValue = oldValue - 10;
        int excepted = 10;
        int actual = newValue;
        Assert.assertEquals(excepted, actual);
    }
}
