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
    private final FruitOperation addOperation = new AddOperation();

    @Before
    public void setUp() {
        Storage.fruits.put(new Fruit("banana"), 150);
    }

    @Test
    public void applySupplyOperation_Ok() {
        FruitRecordDto fruitRecordDto = new FruitRecordDto(OperationType.RETURN, "banana", 100);
        int actual = addOperation.apply(fruitRecordDto);
        assertEquals(EXPECTED, actual);
    }

    @Test
    public void applyReturnOperation_Ok() {
        FruitRecordDto fruitRecordDto = new FruitRecordDto(OperationType.SUPPLY, "banana", 100);
        int actual = addOperation.apply(fruitRecordDto);
        assertEquals(EXPECTED, actual);
    }
}
