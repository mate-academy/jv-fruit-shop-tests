package core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class OperationHandlerExceptionTest {
    @Test
    public void testOperationHandlerException_Message_notOk() {
        String errorMessage = "Error occurred while processing the operation.";
        OperationHandlerException exception = new OperationHandlerException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testOperationHandlerException_NullMessage_notOk() {
        OperationHandlerException exception = new OperationHandlerException(null);
        assertNull(exception.getMessage());
    }
}
