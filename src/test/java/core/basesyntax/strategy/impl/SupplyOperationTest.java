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

public class SupplyOperationTest {
    private static OperationHandler supplyOperation;
    private static Fruit fruit;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        supplyOperation = new SupplyOperation();
        fruit = new Fruit("apple");
    }

    @Test
    public void execute_validData_ok() {
        Storage.stock.put(fruit, 100);
        Integer expected = 150;
        supplyOperation.execute(fruit, 50);
        Assert.assertEquals(
                "The method must correctly add the quantity.",
                expected, Storage.stock.get(fruit));
    }

    @Test
    public void execute_ignoringNullObject_ok() {
        Storage.stock.put(fruit, 100);
        supplyOperation.execute(null, 10);
        Assert.assertEquals(100, (int) Storage.stock.get(fruit));
        supplyOperation.execute(fruit, null);
        Assert.assertEquals(100, (int) Storage.stock.get(fruit));
    }

    @Test
    public void execute_negativeNumber_notOk() {
        exceptionRule.expect(RuntimeException.class);
        Storage.stock.put(fruit, 100);
        supplyOperation.execute(fruit, -10);
    }

    @After
    public void tearDown() {
        Storage.stock.clear();
    }
}
