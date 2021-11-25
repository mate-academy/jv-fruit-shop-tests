package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddOperationHandlerTest {
    private OperationHandler add;

    @Before
    public void setUp() {
        add = new AddOperationHandler();
        Storage.storage.put(new Fruit("banana"), 25);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void operate_validDataReturnAndSupply_ok() {
        int expected = 100;
        add.operate("banana", 75);
        int actual = Storage.storage.get(new Fruit("banana"));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void operate_validDataBalance_ok() {
        int expected = 30;
        add.operate("orange", 30);
        Assert.assertTrue(Storage.storage.containsKey(new Fruit("orange")));
        int actual = Storage.storage.get(new Fruit("orange"));
        Assert.assertEquals(expected, actual);
    }
}
