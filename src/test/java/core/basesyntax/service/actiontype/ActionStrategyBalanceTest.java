package core.basesyntax.service.actiontype;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ActionStrategyBalanceTest {
    public static final int TEST_AMOUNT = 10;
    public static final int EXPECTED_AMOUNT = 0;

    @Test
    public void correctCountBalance_Ok() {
        ActionStrategyBalance actionStrategyBalance = new ActionStrategyBalance();
        int actual = actionStrategyBalance.getNewValue(TEST_AMOUNT);
        assertEquals(EXPECTED_AMOUNT, actual);
    }
}
