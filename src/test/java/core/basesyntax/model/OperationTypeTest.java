package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitRecordDto;
import core.basesyntax.exceptions.IncorectOperationException;
import org.junit.Test;

public class OperationTypeTest {
    private static final String CORRECT_OPERATION = "b";
    private static final String INCORRECT_OPERATION = "f";

    @Test
    public void operationGetCorrect_Ok() {
        FruitRecordDto fruit = new FruitRecordDto(OperationType.BALANCE,
                new Fruit("fruit"), 21);
        OperationType expected = OperationType.getType(CORRECT_OPERATION);
        OperationType actual = fruit.getType();
        assertEquals(actual, expected);
    }

    @Test(expected = IncorectOperationException.class)
    public void operationGetIncorrect_NotOk() {
        OperationType.getType(INCORRECT_OPERATION);
    }
}
