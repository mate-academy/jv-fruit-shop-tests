package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Storage;
import core.basesyntax.service.FruitOperationHandler;
import core.basesyntax.service.OperationType;
import org.junit.Before;
import org.junit.Test;

public class DecreaseOperationImplTest {
    private static FruitOperationHandler fruitOperationHandler;
    private static FruitRecordDto fruitRecordDto;

    @Before
    public void setUp() {
        fruitOperationHandler = new DecreaseOperationImpl();
        fruitRecordDto = new FruitRecordDto(OperationType.PURCHASE,"apple", 25);
        Storage.getFruits().clear();
    }

    @Test
    public void testApply_withDecreaseOperation_isOk() {
        Storage.getFruits().put("apple", 50);
        int newQuantity = fruitOperationHandler.apply(fruitRecordDto);
        assertEquals(25, newQuantity);
    }

    @Test(expected = RuntimeException.class)
    public void testApply_withDecreaseOperationAndNotEnoughFruits_isNotOk() {
        Storage.getFruits().put("apple", 10);
        fruitOperationHandler.apply(fruitRecordDto);
    }
}
