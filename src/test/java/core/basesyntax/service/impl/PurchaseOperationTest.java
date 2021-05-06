package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationTest {
    private static FruitOperationHandler operation;
    private static FruitRecordDto fruitRecordDto;
    private static FruitRecordDto fruitRecordDtoWithNullValue;
    private static FruitRecordDto fruitRecordDtoWithNegativeValue;

    @BeforeClass
    public static void beforeClass() {
        operation = new PurchaseOperation();
        fruitRecordDto = new FruitRecordDto("p", "banana", 20);
        fruitRecordDtoWithNullValue = new FruitRecordDto("p", "banana", null);
        fruitRecordDtoWithNegativeValue = new FruitRecordDto("p", "banana", -1);
    }

    @Test
    public void getType_Ok() {
        String expected = "p";
        String actual = operation.getOperationType();
        assertEquals(actual, expected);
    }

    @Test
    public void executePurchaseOperation_Ok() {
        Storage.fruits.put(new Fruit("banana"), 30);
        int expected = 10;
        int actual = operation.apply(fruitRecordDto);
        assertEquals(actual, expected);
    }

    @Test
    public void executePurchaseOperationWithNullQuantity_Ok() {
        Storage.fruits.put(new Fruit("banana"), 30);
        int expected = 30;
        int actual = operation.apply(fruitRecordDtoWithNullValue);
        assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void executePurchaseOperationWithWithHighQuantity_Ok() {
        Storage.fruits.put(new Fruit("banana"), 5);
        operation.apply(fruitRecordDto);
    }

    @Test(expected = RuntimeException.class)
    public void executePurchaseOperationWithWithNegativeQuantity_Ok() {
        Storage.fruits.put(new Fruit("banana"), 5);
        operation.apply(fruitRecordDtoWithNegativeValue);
    }
}
