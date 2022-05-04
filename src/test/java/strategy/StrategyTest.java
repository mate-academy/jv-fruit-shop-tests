package strategy;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class StrategyTest {
    private static final Strategy strategy = new Strategy();

    @Test
    public void invalidOutput_Ok() {
        OperationHandler expected = new PurchaseOperationHandler();
        OperationHandler actual = strategy.get("b");
        assertNotEquals(expected, actual);
    }
}
