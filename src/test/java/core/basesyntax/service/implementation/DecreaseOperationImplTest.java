package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Storage;
import core.basesyntax.service.FruitOperationHandler;
import core.basesyntax.service.OperationType;
import org.junit.Before;
import org.junit.Test;

public class DecreaseOperationImplTest {
    private static final FruitOperationHandler decreaseOperation = new DecreaseOperationImpl();
    private static final FruitRecordDto fruitRecordDto =
            new FruitRecordDto(OperationType.PURCHASE,"apple", 25);

    @Before
    public void cleanMapDB() {
        Storage.getFruits().clear();
    }

    @Test
    public void testApply_withDecreaseOperation_isOk() {
        Storage.getFruits().put("apple", 50);
        int newQuantity = decreaseOperation.apply(fruitRecordDto);
        assertEquals(25, newQuantity);
    }

    @Test(expected = RuntimeException.class)
    public void testApply_withDecreaseOperationAndNotEnoughFruits_isNotOk() {
        Storage.getFruits().put("apple", 10);
        decreaseOperation.apply(fruitRecordDto);
    }
}
