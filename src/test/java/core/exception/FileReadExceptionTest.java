package core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FileReadExceptionTest {
    @Test
    public void testFileReadException_Message_notOk() {
        String errorMessage = "Error occurred while reading the file.";
        FileReadException exception = new FileReadException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testFileReadException_NullMessage_notOk() {
        FileReadException exception = new FileReadException(null);
        assertEquals(null, exception.getMessage());
    }
}
