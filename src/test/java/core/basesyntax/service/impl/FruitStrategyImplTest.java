package core.basesyntax.service.impl;

import org.junit.Test;
import core.basesyntax.service.FruitStrategy;
import core.basesyntax.strategy.*;
import static org.junit.Assert.assertEquals;

public class FruitStrategyImplTest {
    private final FruitStrategy strategy = new FruitStrategyImpl();
    private FruitOperation expectedOperation;
    private FruitOperation actualOperation;

    @Test(expected = RuntimeException.class)
    public void makeOperationWithWrongAbbreviation_notOk() {strategy.makeOperation("d");
    }

    @Test
    public void makeBalanceOperation_Ok() {
        expectedOperation = new BalanceOperation();
        actualOperation = strategy.makeOperation("b");
        assertEquals(expectedOperation.getClass(), actualOperation.getClass());
    }

    @Test
    public void makeSupplyOperation_Ok() {
        expectedOperation = new SupplyOperation();
        actualOperation = strategy.makeOperation("s");
        assertEquals(expectedOperation.getClass(), actualOperation.getClass());
    }

    @Test
    public void makePurchaseOperation_Ok() {
        expectedOperation = new PurchaseOperation();
        actualOperation = strategy.makeOperation("p");
        assertEquals(expectedOperation.getClass(), actualOperation.getClass());
    }

    @Test
    public void makeReturnOperation_Ok() {
        expectedOperation = new ReturnOperation();
        actualOperation = strategy.makeOperation("r");
        assertEquals(expectedOperation.getClass(), actualOperation.getClass());
    }
}
