package core.basesyntax.strategy.operation.impl;

import core.basesyntax.db.Storage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationImplTest {
    private static ReturnOperationImpl returnOperation;
    private static String PINEAPPLE = "pineapple";
    private static int PINEAPPLE_VALUE = 20;

    @BeforeClass
    public static void beforeClass() {
        returnOperation = new ReturnOperationImpl();
    }

    @Test
    public void applyOperation_correctData_ok() {
        returnOperation.putNewBalanceToFruit(PINEAPPLE, PINEAPPLE_VALUE);
        int expected = PINEAPPLE_VALUE;
        int actual = Storage.FRUIT_STORAGE.get(PINEAPPLE);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void applyOperation_ok() {
        Storage.FRUIT_STORAGE.put(PINEAPPLE, 20);
        int oldValue = Storage.FRUIT_STORAGE.get(PINEAPPLE);
        int newValue = oldValue + 30;
        Storage.FRUIT_STORAGE.put(PINEAPPLE, newValue);
    }
}
