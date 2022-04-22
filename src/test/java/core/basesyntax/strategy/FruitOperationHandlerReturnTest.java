package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FruitOperationHandlerReturnTest {
    private final FruitOperationHandlerReturn retur = new FruitOperationHandlerReturn();

    @Test
    public void returnOerationReturn_Ok() {
        assertEquals(20, retur.handle(10, 10));
    }
}
