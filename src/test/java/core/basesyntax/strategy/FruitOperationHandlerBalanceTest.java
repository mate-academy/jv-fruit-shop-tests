package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FruitOperationHandlerBalanceTest {
    private FruitOperationHandlerBalance balance = new FruitOperationHandlerBalance();

    @Test
    public void returnOerationBalance_Ok() {
        assertEquals(20, balance.handle(10, 10));
    }
}
