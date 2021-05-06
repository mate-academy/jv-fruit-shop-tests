package core.basesyntax.basesyntax.service.impl;

import core.basesyntax.basesyntax.db.Storage;
import core.basesyntax.basesyntax.model.Fruit;
import core.basesyntax.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.basesyntax.service.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddOperationHandlerTest {
    private OperationHandler addOperationHandler = new AddOperationHandler();

    @Before
    public void setUp() throws Exception {
        Storage.fruits.put(new Fruit("banana"), 100);
        Storage.fruits.put(new Fruit("apple"), 0);
    }

    @After
    public void tearDown() throws Exception {
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
        int expected = 50;
        int actual = addOperationHandler.apply(new FruitRecordDto("r", "apple", 50));
        Assert.assertEquals(expected, actual);
    }
}
