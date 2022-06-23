package core.basesyntax.service.actiontype;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ActionStrategyProducerTest {
    public static final int TEST_AMOUNT = 10;
    public static final int EXPECTED_AMOUNT = 10;

    @Test
    public void correctCountProducer_Ok() {
        ActionStrategyProducer actionStrategyProducer = new ActionStrategyProducer();
        int actual = actionStrategyProducer.getNewValue(TEST_AMOUNT);
        assertEquals(EXPECTED_AMOUNT, actual);
    }
}
