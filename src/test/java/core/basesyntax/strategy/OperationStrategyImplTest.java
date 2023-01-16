package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationStrategy;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategyImpl strategy;
    private static FruitTransaction transaction;

    @BeforeClass
    public static void setUp() {
        strategy = new OperationStrategyImpl();
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
    }

    @Test
    public void getOperationStrategy_balanceOperation_ok() {
        Class<?> expected = BalanceOperationStrategy.class;
        Class<?> actual = strategy.getOperationStrategy(transaction).getClass();
        Assert.assertEquals(expected, actual);
    }
}
