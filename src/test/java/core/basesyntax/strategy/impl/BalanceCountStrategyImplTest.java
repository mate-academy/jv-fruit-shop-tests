package core.basesyntax.strategy.impl;

import core.basesyntax.strategy.OperationCalculator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceCountStrategyImplTest {
    private static OperationCalculator operationCalculator;
    private static int currentAmount;
    private static int operationAmount;

    @BeforeClass
    public static void beforeClass() {
        operationCalculator = new BalanceCountStrategyImpl();
        currentAmount = 0;
        operationAmount = 5;
    }

    @Test
    public void count_balance_ok() {
        int expected = 5;
        int actual = operationCalculator.count(currentAmount, operationAmount);
        Assert.assertEquals("Balance should be updated properly", expected, actual);
    }
}
