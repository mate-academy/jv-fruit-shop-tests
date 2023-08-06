package core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.testng.annotations.Test;

public class FileReadExceptionTest {
    @Test
    public void testFileReadException_Message() {
        String errorMessage = "Error occurred while reading the file.";
        FileReadException exception = new FileReadException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testFileReadException_NullMessage() {
        FileReadException exception = new FileReadException(null);
        assertEquals(null, exception.getMessage());
    }
}
