package core.basesyntax.service.impl;

import core.basesyntax.service.FruitStrategy;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.FruitOperation;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import org.junit.Assert;
import org.junit.Test;

public class FruitStrategyImplTest {
    private final FruitStrategy strategy = new FruitStrategyImpl();
    private FruitOperation expectedOperation;
    private FruitOperation actualOperation;

    @Test(expected = RuntimeException.class)
    public void makeOperationWithWrongAbbreviation_notOk() {
        strategy.makeOperation("d");
    }

    @Test
    public void makeBalanceOperation_Ok() {
        expectedOperation = new BalanceOperation();
        actualOperation = strategy.makeOperation("b");
        Assert.assertEquals(expectedOperation.getClass(), actualOperation.getClass());
    }

    @Test
    public void makeSupplyOperation_Ok() {
        expectedOperation = new SupplyOperation();
        actualOperation = strategy.makeOperation("s");
        Assert.assertEquals(expectedOperation.getClass(), actualOperation.getClass());
    }

    @Test
    public void makePurchaseOperation_Ok() {
        expectedOperation = new PurchaseOperation();
        actualOperation = strategy.makeOperation("p");
        Assert.assertEquals(expectedOperation.getClass(), actualOperation.getClass());
    }

    @Test
    public void makeReturnOperation_Ok() {
        expectedOperation = new ReturnOperation();
        actualOperation = strategy.makeOperation("r");
        Assert.assertEquals(expectedOperation.getClass(), actualOperation.getClass());
    }
}
