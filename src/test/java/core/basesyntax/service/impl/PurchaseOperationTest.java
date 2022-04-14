package core.basesyntax.service.impl;

import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitOperationHandler;
import core.basesyntax.service.Operation;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationTest {
    private FruitOperationHandler operationHandler = new PurchaseOperation();

    @BeforeClass
    public static void beforeClass() throws Exception {
        Storage.fruits.put(new Fruit("banana"), 50);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void apply_addOperation_Ok() {
        int expected = 20;
        int actual = operationHandler
                .apply(new FruitRecordDto(Operation.PURCHASE, "banana", 30));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_addOperation_NotOk() {
        operationHandler.apply(new FruitRecordDto(Operation.PURCHASE, "banana", null));
        operationHandler.apply(new FruitRecordDto(Operation.PURCHASE, "banana", -30));
    }
}
