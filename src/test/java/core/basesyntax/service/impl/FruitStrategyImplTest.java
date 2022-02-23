package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FruitStrategy;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.FruitOperation;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitStrategyImplTest {
    private static FruitStrategy strategy;
    private FruitOperation expectedOperation;
    private FruitOperation actualOperation;

    @BeforeClass
    public static void beforeClass() {
        strategy = new FruitStrategyImpl();
    }

    @Test(expected = RuntimeException.class)
    public void makeOperation_WithWrongAbbreviation_notOk() {
        strategy.makeOperation("d");
    }

    @Test
    public void makeOperation_BalanceOperation_Ok() {
        expectedOperation = new BalanceOperation();
        actualOperation = strategy.makeOperation("b");
        assertEquals(expectedOperation.getClass(), actualOperation.getClass());
    }

    @Test
    public void makeOperation_SupplyOperation_Ok() {
        expectedOperation = new SupplyOperation();
        actualOperation = strategy.makeOperation("s");
        assertEquals(expectedOperation.getClass(), actualOperation.getClass());
    }

    @Test
    public void makeOperation_PurchaseOperation_Ok() {
        expectedOperation = new PurchaseOperation();
        actualOperation = strategy.makeOperation("p");
        assertEquals(expectedOperation.getClass(), actualOperation.getClass());
    }

    @Test
    public void makeOperation_ReturnOperation_Ok() {
        expectedOperation = new ReturnOperation();
        actualOperation = strategy.makeOperation("r");
        assertEquals(expectedOperation.getClass(), actualOperation.getClass());
    }
}
