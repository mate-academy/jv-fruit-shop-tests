package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.service.operations.OperationType;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordDtoTest {
    private static FruitRecordDto fruitRecordDto;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitRecordDto = new FruitRecordDto(OperationType.b,"apple",12);
    }

    @Test
    public void getOperationType_Ok() {
        assertEquals(OperationType.b,fruitRecordDto.getOperationType());
    }

    @Test
    public void getFruitName_Ok() {
        assertEquals("apple",fruitRecordDto.getFruitName());
    }

    @Test
    public void getFruitQuantity_Ok() {
        assertEquals(12,Long.parseLong(Integer.toString(fruitRecordDto.getFruitCount())));
    }

    @Test
    public void getIncorrectOperationType_NotOk() {
        assertNotEquals(OperationType.p,fruitRecordDto.getOperationType());
    }

    @Test
    public void getIncorrectFruitName_NotOk() {
        assertNotEquals("coconut",fruitRecordDto.getFruitName());
    }

    @Test
    public void getIncorrectFruitQuantity_NotOk() {
        assertNotEquals(-1,Long.parseLong(Integer.toString(fruitRecordDto.getFruitCount())));
    }
}
