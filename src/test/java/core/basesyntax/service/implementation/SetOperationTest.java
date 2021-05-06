package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Storage;
import core.basesyntax.service.FruitOperationHandler;
import core.basesyntax.service.OperationType;
import org.junit.Before;
import org.junit.Test;

public class SetOperationTest {
    private static final FruitOperationHandler decreaseOperation = new SetOperation();
    private static final FruitRecordDto fruitRecordDto =
            new FruitRecordDto(OperationType.PURCHASE,"apple", 250);

    @Before
    public void cleanMapDB() {
        Storage.getFruits().clear();
    }

    @Test
    public void testApply_withSetOperation_isOk() {
        Storage.getFruits().put("apple", 50);
        int newQuantity = decreaseOperation.apply(fruitRecordDto);
        assertEquals(250, newQuantity);
    }
}
