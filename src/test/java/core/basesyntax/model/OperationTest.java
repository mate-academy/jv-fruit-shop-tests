package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class OperationTest {
    @Test
    public void getByCode_validData_Ok() {
        String code = "s";
        Operation expected = Operation.SUPPLY;
        Operation actual = Operation.getByCode(code);
        assertEquals(expected, actual);
    }

    @Test
    public void getByCode_Null_NotOk() {
        String code = null;
        Exception exception = assertThrows(RuntimeException.class, () -> Operation.getByCode(code));
        String expectedMessage = "Invalid code " + code;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getByCode_inValidData_NotOk() {
        String code = "a";
        Exception exception = assertThrows(RuntimeException.class,
                () -> Operation.getByCode(code));
        String expectedMessage = "Invalid code " + code;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
