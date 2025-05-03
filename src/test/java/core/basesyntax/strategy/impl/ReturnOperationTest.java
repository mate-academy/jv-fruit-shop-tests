package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationTest {
    private static OperationHandler returnOperation;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        returnOperation = new ReturnOperation();
        fruit = new Fruit("apple");
    }

    @Test
    public void execute_validData_ok() {
        Storage.stock.put(fruit, 100);
        Integer expected = 150;
        returnOperation.execute(fruit, 50);
        Assert.assertEquals(
                "The method must correctly add the quantity.",
                expected, Storage.stock.get(fruit));
    }

    @Test
    public void execute_ignoringNullObject_ok() {
        Storage.stock.put(fruit, 100);
        returnOperation.execute(null, 10);
        Assert.assertEquals(100, (int) Storage.stock.get(fruit));
        returnOperation.execute(fruit, null);
        Assert.assertEquals(100, (int) Storage.stock.get(fruit));
    }

    @Test(expected = RuntimeException.class)
    public void execute_negativeNumber_notOk() {
        Storage.stock.put(fruit, 100);
        returnOperation.execute(fruit, -10);
    }

    @After
    public void tearDown() {
        Storage.stock.clear();
    }
}
