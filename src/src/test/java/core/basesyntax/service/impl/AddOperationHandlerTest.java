package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.OperationHandler;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    private OperationHandler addOperationHandler = new AddOperationHandler();

    @BeforeClass
    public static void beforeClass() throws Exception {
        Storage.fruits.put(new Fruit("banana"), 100);
        Storage.fruits.put(new Fruit("apple"), 0);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void apply_emptyStorage_isOk() {
        int expected = 50;
        int actual = addOperationHandler.apply(new FruitRecordDto("r", "apple", 50));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_additionToStorageTypeSupply_isOk() {
        int expected = 150;
        int actual = addOperationHandler.apply(new FruitRecordDto("s", "banana", 50));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_additionToStorageTypeReturn_isOk() {
        int expected = 100;
        int actual = addOperationHandler.apply(new FruitRecordDto("r", "apple", 50));
        Assert.assertEquals(expected, actual);
    }
}
