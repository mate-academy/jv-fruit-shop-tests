package core.basesyntax.strategy.operation.impl;

import core.basesyntax.db.Storage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationImplTest {
    private static BalanceOperationImpl balanceOperation;
    private static String APPLE = "apple";
    private static int APPLE_VALUE = 10;

    @BeforeClass
    public static void beforeClass() {
        balanceOperation = new BalanceOperationImpl();
    }

    @Test
    public void applyOperation_correctData_ok() {
        balanceOperation.putNewBalanceToFruit(APPLE, APPLE_VALUE);
        int expected = APPLE_VALUE;
        int actual = Storage.FRUIT_STORAGE.get(APPLE);
        Assert.assertEquals(expected, actual);
    }
}
