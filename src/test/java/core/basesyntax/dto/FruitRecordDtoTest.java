package core.basesyntax.dto;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Operation;
import org.junit.Test;

public class FruitRecordDtoTest {
    private static final FruitRecordDto fruitRecordDto = new FruitRecordDto(Operation.BALANCE,
            "banana", 40);

    @Test
    public void getOperationType_correctType_Ok() {
        assertEquals(Operation.BALANCE, fruitRecordDto.getOperationType());
    }

    @Test
    public void getFruitName() {
        assertEquals("banana", fruitRecordDto.getFruitName());
    }

    @Test
    public void getQuantity() {
        assertEquals(Integer.valueOf(40), fruitRecordDto.getQuantity());
    }
}
