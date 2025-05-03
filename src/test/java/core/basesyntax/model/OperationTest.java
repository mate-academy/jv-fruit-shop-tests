package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OperationTest {

    @Test
    void getOperationByCode_ShouldReturnCorrectOperation() {
        assertEquals(Operation.BALANCE, Operation.getOperationByCode("b"));
        assertEquals(Operation.SUPPLY, Operation.getOperationByCode("s"));
        assertEquals(Operation.PURCHASE, Operation.getOperationByCode("p"));
        assertEquals(Operation.RETURN, Operation.getOperationByCode("r"));
    }

    @Test
    void getOperationByCode_ShouldThrowException_WhenCodeIsInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                Operation.getOperationByCode("x"));

        assertEquals("There is no operation with code: x", exception.getMessage());
    }

    @Test
    void getOperationByCode_ShouldThrowException_WhenCodeIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                Operation.getOperationByCode(null));

        assertEquals("There is no operation with code: null", exception.getMessage());
    }

    @Test
    void getOperationByCode_ShouldThrowException_WhenCodeIsEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                Operation.getOperationByCode(""));

        assertEquals("There is no operation with code: ", exception.getMessage());
    }
}
