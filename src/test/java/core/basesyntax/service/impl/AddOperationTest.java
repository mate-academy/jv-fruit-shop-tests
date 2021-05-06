package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitOperation;
import org.junit.Before;
import org.junit.Test;

public class AddOperationTest {
    private static final int EXPECTED = 250;

    @Before
    public void setUp() {
        Storage.fruits.put(new Fruit("banana"), 150);
    }

    @Test
    public void applySupplyOperation_Ok() {
        FruitRecordDto fruitRecordDto = new FruitRecordDto(OperationType.RETURN, "banana", 100);
        FruitOperation returnOperation = new AddOperation();
        int actual = returnOperation.apply(fruitRecordDto);
        assertEquals(EXPECTED, actual);
    }

    @Test
    public void applyReturnOperation_Ok() {
        FruitRecordDto fruitRecordDto = new FruitRecordDto(OperationType.SUPPLY, "banana", 100);
        FruitOperation supplyOperation = new AddOperation();
        int actual = supplyOperation.apply(fruitRecordDto);
        assertEquals(EXPECTED, actual);
    }
}
