package core.basesyntax.service.implementation;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Storage;
import core.basesyntax.service.FruitOperationHandler;
import core.basesyntax.service.OperationType;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class IncreaseOperationImplTest extends TestCase {
    private static final FruitOperationHandler DECREASE = new IncreaseOperationImpl();
    private static final FruitRecordDto FRUIT_SUPPLY =
            new FruitRecordDto(OperationType.SUPPLY, "apple", 25);
    private static final FruitRecordDto FRUIT_RETURN =
            new FruitRecordDto(OperationType.RETURN, "apple", 25);

    @Before
    public void cleanMapDB() {
        Storage.getFruits().clear();
    }

    @Test
    public void testApply_withSupplyOperation_isOk() {
        Storage.getFruits().put("apple", 50);
        int newQuantity = DECREASE.apply(FRUIT_SUPPLY);
        assertEquals(75, newQuantity);
    }

    @Test
    public void testApply_withReturnOperation_isOk() {
        Storage.getFruits().put("apple", 50);
        int newQuantity = DECREASE.apply(FRUIT_RETURN);
        assertEquals(75, newQuantity);
    }

}
