package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.Strategy;
import org.junit.Before;
import org.junit.Test;

public class StrategyImplTest {
    private Strategy testStrategy;
    private OperationHandler balance;

    @Before
    public void setUp() {
        testStrategy = new StrategyImpl();
        balance = new OperationHandlerBalanceImpl();
    }

    @Test
    public void addStrategyTypeMethod_ok() {
        testStrategy.addStrategyType("b", balance);
        assertEquals(testStrategy.getStrategyType("b"), balance);
    }
}
