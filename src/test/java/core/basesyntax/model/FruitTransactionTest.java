package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class FruitTransactionTest {
    @Test
    public void getByCode_validData_Ok() {
        String code = "b";
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getByCode(code);
        assertEquals(expected, actual);
    }

    @Test
    public void getByWrongCode_inValidData_NotOk() {
        String code = "w";
        Exception exception = assertThrows(RuntimeException.class,
                () -> FruitTransaction.Operation.getByCode(code));
        String expectedMessage = "This code " + code + " is incorrect!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getByCode_Null_NotOk() {
        String code = null;
        Exception exception = assertThrows(RuntimeException.class,
                () -> FruitTransaction.Operation.getByCode(code));
        String expectedMessage = "This code " + code + " is incorrect!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
