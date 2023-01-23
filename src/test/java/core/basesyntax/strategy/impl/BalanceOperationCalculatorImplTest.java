package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.OperationCalculator;
import org.junit.Test;

public class BalanceOperationCalculatorImplTest {
    private static final int VALID_BALANCE = 28;
    private static final int VALID_QUANTITY = 13;
    private static final int ZERO_QUANTITY = 0;
    private static final int ZERO_BALANCE = 0;
    private OperationCalculator balanceOperationCalculator;

    @Test
    public void calculateBalance_ok() {
        balanceOperationCalculator = new BalanceOperationCalculatorImpl();
        int actual = balanceOperationCalculator.calculate(VALID_BALANCE, VALID_QUANTITY);
        assertEquals(VALID_QUANTITY, actual);
    }

    @Test
    public void calculateBalance_ZeroQuantity_ok() {
        balanceOperationCalculator = new BalanceOperationCalculatorImpl();
        balanceOperationCalculator.calculate(VALID_BALANCE, ZERO_QUANTITY);
    }

    @Test
    public void calculateBalanceZeroBalance_ok() {
        balanceOperationCalculator = new BalanceOperationCalculatorImpl();
        balanceOperationCalculator.calculate(ZERO_BALANCE, VALID_QUANTITY);
    }
}
