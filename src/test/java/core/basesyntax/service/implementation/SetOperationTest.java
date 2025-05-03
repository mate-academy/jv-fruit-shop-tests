package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Storage;
import core.basesyntax.service.FruitOperationHandler;
import core.basesyntax.service.OperationType;
import org.junit.BeforeClass;
import org.junit.Test;

public class SetOperationTest {
    private static FruitOperationHandler SET_OPERATION;
    private static FruitRecordDto FRUIT_RECORD_DTO;

    @BeforeClass
    public static void setUp() {
        SET_OPERATION = new SetOperation();
        FRUIT_RECORD_DTO =
                new FruitRecordDto(OperationType.BALANCE,"apple", 250);
        Storage.getFruits().clear();
    }

    @Test
    public void testApply_withSetOperation_isOk() {
        Storage.getFruits().put("apple", 50);
        int newQuantity = SET_OPERATION.apply(FRUIT_RECORD_DTO);
        assertEquals(250, newQuantity);
    }
}
