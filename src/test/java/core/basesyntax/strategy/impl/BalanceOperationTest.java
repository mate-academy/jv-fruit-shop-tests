package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationTest {
    private static OperationHandler balanceOperation;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        balanceOperation = new BalanceOperation();
        fruit = new Fruit("apple");
    }

    @Test
    public void execute_validData_ok() {
        Integer expected = 100;
        balanceOperation.execute(fruit, expected);
        Assert.assertEquals(
                "The method must declare the correct amount of the corresponding object.",
                expected, Storage.stock.get(fruit));
        expected = 70;
        balanceOperation.execute(fruit, expected);
        Assert.assertEquals("The method must replace the current value.",
                expected, Storage.stock.get(fruit));
    }

    @Test(expected = RuntimeException.class)
    public void execute_nullQuantity_notOk() {
        balanceOperation.execute(fruit, null);
    }

    @Test(expected = RuntimeException.class)
    public void execute_nullFruit_notOk() {
        balanceOperation.execute(null, 100);
    }

    @Test(expected = RuntimeException.class)
    public void execute_negativeNumber_notOk() {
        Storage.stock.put(fruit, 100);
        balanceOperation.execute(fruit, -10);
    }

    @After
    public void tearDown() {
        Storage.stock.clear();
    }
}
