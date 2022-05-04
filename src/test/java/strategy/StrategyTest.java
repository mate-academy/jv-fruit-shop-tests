package strategy;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class StrategyTest {
    @Test
    public void invalidOutput_Ok() {
        OperationHandler actual = new Strategy().get("y");
        assertNull(actual);
    }
}
