package core.basesyntax.strategy.impl;

import core.basesyntax.strategy.OperationCalculator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnCountStrategyImplTest {
    private static OperationCalculator operationCalculator;
    private static int currentAmount;
    private static int operationAmount;

    @BeforeClass
    public static void beforeClass() {
        operationCalculator = new ReturnCountStrategyImpl();
        currentAmount = 20;
        operationAmount = 5;
    }

    @Test
    public void count_return_ok() {
        int expected = 25;
        int actual = operationCalculator.count(currentAmount, operationAmount);
        Assert.assertEquals("Amount should be updated properly", expected, actual);
    }

    @Test
    public void count_return_notOk() {
        int expected = 14;
        int actual = operationCalculator.count(currentAmount, operationAmount);
        Assert.assertNotEquals("Amount should be updated properly", expected, actual);
    }
}
