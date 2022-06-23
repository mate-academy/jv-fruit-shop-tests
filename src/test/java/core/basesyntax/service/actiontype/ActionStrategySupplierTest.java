package core.basesyntax.service.actiontype;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ActionStrategySupplierTest {
    public static final int TEST_AMOUNT = 10;
    public static final int EXPECTED_AMOUNT = -10;

    @Test
    public void correctCountSupplier_Ok() {
        ActionStrategySupplier actionStrategySupplier = new ActionStrategySupplier();
        int actual = actionStrategySupplier.getNewValue(TEST_AMOUNT);
        assertEquals(EXPECTED_AMOUNT, actual);
    }
}
