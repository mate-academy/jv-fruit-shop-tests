package core.basesyntax.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class InsufficientQuantityExceptionTest {
    @Test
    void exception_printCorrectMessage_ok() {
        String message = "Insufficient quantity available";

        InsufficientQuantityException exception = assertThrows(
                InsufficientQuantityException.class,
                () -> {
                    throw new InsufficientQuantityException(message);
                }
        );

        assertEquals(message, exception.getMessage());
    }
}
