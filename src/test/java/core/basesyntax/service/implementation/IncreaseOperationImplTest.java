package core.basesyntax.service.implementation;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Storage;
import core.basesyntax.service.FruitOperationHandler;
import core.basesyntax.service.OperationType;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class IncreaseOperationImplTest {
    private static FruitOperationHandler DECREASE;
    private static FruitRecordDto FRUIT_SUPPLY;
    private static FruitRecordDto FRUIT_RETURN;

    @BeforeClass
    public static void setUp() {
        DECREASE = new IncreaseOperationImpl();
        FRUIT_SUPPLY = new FruitRecordDto(OperationType.SUPPLY, "apple", 25);
        FRUIT_RETURN = new FruitRecordDto(OperationType.RETURN, "apple", 25);
        Storage.getFruits().clear();
    }

    @Test
    public void testApply_withSupplyOperation_isOk() {
        Storage.getFruits().put("apple", 50);
        int newQuantity = DECREASE.apply(FRUIT_SUPPLY);
        Assert.assertEquals(75, newQuantity);
    }

    @Test
    public void testApply_withReturnOperation_isOk() {
        Storage.getFruits().put("apple", 50);
        int newQuantity = DECREASE.apply(FRUIT_RETURN);
        Assert.assertEquals(75, newQuantity);
    }

}
