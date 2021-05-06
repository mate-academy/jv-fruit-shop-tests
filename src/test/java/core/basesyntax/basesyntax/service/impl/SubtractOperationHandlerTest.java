package core.basesyntax.basesyntax.service.impl;

import core.basesyntax.basesyntax.db.Storage;
import core.basesyntax.basesyntax.model.Fruit;
import core.basesyntax.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.basesyntax.service.OperationHandler;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubtractOperationHandlerTest {
    private OperationHandler subtractOperationHandler = new SubtractOperationHandler();

    @BeforeClass
    public static void beforeClass() throws Exception {
        Storage.fruits.put(new Fruit("banana"), 100);
        Storage.fruits.put(new Fruit("apple"), 100);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void apply_subtractOperation_isOk() {
        int expected = 50;
        int actual = subtractOperationHandler.apply(new FruitRecordDto("p",
                "apple", 50));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_subtractOperation_NotOk() {
        subtractOperationHandler.apply(new FruitRecordDto("p", "banana", 120));
    }
}
