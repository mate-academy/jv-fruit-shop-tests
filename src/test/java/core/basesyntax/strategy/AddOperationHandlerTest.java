package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static OperationHandler addHandler;

    @BeforeClass
    public static void setUp() {
        addHandler = new AddOperationHandler();
    }

    @Test
    public void operate_validDataReturnAndSupply_ok() {
        Storage.storage.clear();
        Storage.storage.put(new Fruit("banana"), 25);
        int expected = 100;
        addHandler.operate("banana", 75);
        int actual = Storage.storage.get(new Fruit("banana"));
        Assert.assertEquals(expected, actual);
    }

}
