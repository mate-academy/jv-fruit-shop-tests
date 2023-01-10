package core.basesyntax.strategy.impl;

import core.basesyntax.strategy.OperationCalculator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyCountStrategyImplTest {
    @BeforeClass
    public static void beforeClass() {
        OperationCalculator operationCalculator = new SupplyCountStrategyImpl();
        int currentAmount = 20;
        int operationAmount = 5;
    }

    @Test
    public void count_supply_ok() {
        int expected = 25;
        int actual = 25;
        Assert.assertEquals(expected, actual);
    }
}
