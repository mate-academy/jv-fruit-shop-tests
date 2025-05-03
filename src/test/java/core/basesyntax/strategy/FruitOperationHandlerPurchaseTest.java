package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FruitOperationHandlerPurchaseTest {
    private final FruitOperationHandlerPurchase purchase = new FruitOperationHandlerPurchase();

    @Test
    public void returnOerationPurchase_Ok() {
        assertEquals(10, purchase.handle(20, 10));
    }
}
