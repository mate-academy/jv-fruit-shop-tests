package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationTest {
    private static FruitOperationHandler operation;
    private static FruitRecordDto fruitRecordDto;
    private static FruitRecordDto fruitRecordDtoWithNullValue;
    private static FruitRecordDto fruitRecordDtoWithNegativeValue;

    @BeforeClass
    public static void beforeClass() {
        operation = new SupplyOperation();
        fruitRecordDto = new FruitRecordDto("s","banana",20);
        fruitRecordDtoWithNullValue = new FruitRecordDto("s", "banana", null);
        fruitRecordDtoWithNegativeValue = new FruitRecordDto("s","banana",-1);
    }

    @Test
    public void getType_Ok() {
        String expected = "s";
        String actual = operation.getOperationType();
        assertEquals(actual, expected);
    }

    @Test
    public void executeSupplyOperation_Ok() {
        Storage.fruits.put(new Fruit("banana"), 30);
        int expected = 50;
        int actual = operation.apply(fruitRecordDto);
        assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void executeSupplyOperationWithWithNegativeQuantity_Ok() {
        Storage.fruits.put(new Fruit("banana"), 5);
        operation.apply(fruitRecordDtoWithNegativeValue);
    }

    @Test
    public void executeSupplyOperationWithNullQuantity_Ok() {
        Storage.fruits.put(new Fruit("banana"), 30);
        int expected = 30;
        int actual = operation.apply(fruitRecordDtoWithNullValue);
        assertEquals(actual, expected);
    }
}
