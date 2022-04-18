package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FruitOperationHandlerSupplyTest {
    private final FruitOperationHandlerSupply supply = new FruitOperationHandlerSupply();

    @Test
    public void returnOerationSupply_Ok() {
        assertEquals(20, supply.handle(10, 10));
    }
}
