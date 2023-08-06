package core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.testng.annotations.Test;

public class OperationHandlerExceptionTest {
    @Test
    public void testOperationHandlerException_Message() {
        String errorMessage = "Error occurred while processing the operation.";
        OperationHandlerException exception = new OperationHandlerException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testOperationHandlerException_NullMessage() {
        OperationHandlerException exception = new OperationHandlerException(null);
        assertEquals(null, exception.getMessage());
    }
}
