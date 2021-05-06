package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationTest {
    private static FruitOperationHandler fruitOperationHandler;
    private static FruitRecordDto fruitRecordDto;
    private static FruitRecordDto fruitRecordDtoWithNullValue;
    private static String operationType;

    @BeforeClass
    public static void beforeClass() {
        fruitOperationHandler = new BalanceOperation();
        fruitRecordDto = new FruitRecordDto("b","banana",20);
        fruitRecordDtoWithNullValue = new FruitRecordDto("b", "banana", null);
        operationType = fruitOperationHandler.getOperationType();
    }

    @Test
    public void getType_Ok() {
        String expected = "b";
        String actual = operationType;
        assertEquals(expected, actual);
    }

    @Test
    public void getBalanceFromMap_Ok() {
        int expected = 20;
        int actual = fruitOperationHandler.apply(fruitRecordDto);
        assertEquals(actual, expected);
    }

    @Test
    public void getBalanceWithNullValue_Ok() {
        int expected = 0;
        int actual = fruitOperationHandler.apply(fruitRecordDtoWithNullValue);
        assertEquals(actual, expected);
    }
}
