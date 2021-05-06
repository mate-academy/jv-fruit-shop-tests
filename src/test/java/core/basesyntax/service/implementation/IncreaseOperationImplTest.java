package core.basesyntax.service.implementation;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Storage;
import core.basesyntax.service.FruitOperationHandler;
import core.basesyntax.service.OperationType;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class IncreaseOperationImplTest extends TestCase {
    private static final FruitOperationHandler decreaseOperation = new IncreaseOperationImpl();
    private static final FruitRecordDto fruitRecordDto =
            new FruitRecordDto(OperationType.PURCHASE, "apple", 25);

    @Before
    public void cleanMapDB() {
        Storage.getFruits().clear();
    }

    @Test
    public void testApply_withIncreaseOperation_isOk() {
        Storage.getFruits().put("apple", 50);
        int newQuantity = decreaseOperation.apply(fruitRecordDto);
        assertEquals(75, newQuantity);
    }
}
