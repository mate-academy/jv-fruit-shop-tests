package core.basesyntax.service.actiontype;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ActionStrategyReturnerTest {
    public static final int TEST_AMOUNT = 10;
    public static final int EXPECTED_AMOUNT = 10;

    @Test
    public void correctCountReturner_Ok() {
        ActionStrategyReturner actionStrategyReturner = new ActionStrategyReturner();
        int actual = actionStrategyReturner.getNewValue(TEST_AMOUNT);
        assertEquals(EXPECTED_AMOUNT, actual);
    }
}

