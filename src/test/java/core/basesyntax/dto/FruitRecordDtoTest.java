package core.basesyntax.dto;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.OperationType;
import org.junit.Test;

public class FruitRecordDtoTest {
    private static final Integer quantity = 777;
    private static final FruitRecordDto fruitRecordDto =
            new FruitRecordDto(OperationType.PURCHASE,"apple", quantity);

    @Test
    public void testGetOperationType_isOK() {
        assertEquals(OperationType.PURCHASE,fruitRecordDto.getOperationType());
    }

    @Test
    public void testGetFruitName_isOk() {
        assertEquals("apple",fruitRecordDto.getFruitName());
    }

    @Test
    public void testGetQuantity_IsOk() {
        assertEquals(quantity, fruitRecordDto.getQuantity());
    }
}
