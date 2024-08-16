package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OperationTest {
    private static final String INVALID_OPERATION_CODE = "m";
    private static final String UNKNOWN_OPERATION_MESSAGE = "Invalid operation type "
            + INVALID_OPERATION_CODE;

    @Test
    void getOperationFromCode_Ok() {
        assertEquals(Operation.BALANCE, Operation.getOperationFromCode(
                "b"), "Code 'b' should correspond to BALANCE operation");
        assertEquals(Operation.SUPPLY, Operation.getOperationFromCode(
                "s"), "Code 's' should correspond to SUPPLY operation");
        assertEquals(Operation.PURCHASE, Operation.getOperationFromCode(
                "p"), "Code 'p' should correspond to PURCHASE operation");
        assertEquals(Operation.RETURN, Operation.getOperationFromCode(
                "r"), "Code 'r' should correspond to RETURN operation");
    }

    @Test
    void getOperationFromCode_NotOk() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Operation.getOperationFromCode(INVALID_OPERATION_CODE);
        });

        String expectedMessage = UNKNOWN_OPERATION_MESSAGE;
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage,
                "Exception message should contain 'Invalid operation type m");
    }
}
