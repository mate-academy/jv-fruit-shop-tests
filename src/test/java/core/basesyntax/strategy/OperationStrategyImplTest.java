package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy strategy;

    @BeforeClass
    public static void setUp() {
        strategy = new OperationStrategyImpl();
    }

    @Test
    public void getOperationStrategy_balanceOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 100);
        Class<?> expected = BalanceOperationHandler.class;
        Class<?> actual = strategy.getOperationStrategy(transaction).getClass();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getOperationStrategy_purchaseOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 100);
        Class<?> expected = PurchaseOperationHandler.class;
        Class<?> actual = strategy.getOperationStrategy(transaction).getClass();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getOperationStrategy_returnOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 100);
        Class<?> expected = ReturnOperationHandler.class;
        Class<?> actual = strategy.getOperationStrategy(transaction).getClass();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getOperationStrategy_supplyOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 100);
        Class<?> expected = SupplyOperationHandler.class;
        Class<?> actual = strategy.getOperationStrategy(transaction).getClass();
        Assert.assertEquals(expected, actual);
    }
}
