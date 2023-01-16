package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationStrategyTest {
    private static BalanceOperationStrategy balanceOperationStrategy;

    @BeforeClass
    public static void setUp() {
        balanceOperationStrategy = new BalanceOperationStrategy();
    }

    @Test
    public void calculate_validData_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 50);
        Storage.fruits.clear();
        balanceOperationStrategy.calculate(transaction);
        Integer expected = 50;
        Integer actual = Storage.fruits.get("banana");
        Assert.assertEquals("Wrong balance data", expected, actual);
    }
}
