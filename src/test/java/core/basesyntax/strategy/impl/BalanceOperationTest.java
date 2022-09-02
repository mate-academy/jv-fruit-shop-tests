package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BalanceOperationTest {
    private static OperationHandler balanceOperation;
    private static Fruit fruit;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

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

    @Test
    public void execute_nullQuantity_notOk() {
        exceptionRule.expect(NullPointerException.class);
        balanceOperation.execute(fruit, null);
    }

    @Test
    public void execute_nullFruit_notOk() {
        exceptionRule.expect(NullPointerException.class);
        balanceOperation.execute(null, 100);
    }

    @Test
    public void execute_negativeNumber_notOk() {
        exceptionRule.expect(RuntimeException.class);
        Storage.stock.put(fruit, 100);
        balanceOperation.execute(fruit, -10);
    }

    @After
    public void tearDown() {
        Storage.stock.clear();
    }
}
