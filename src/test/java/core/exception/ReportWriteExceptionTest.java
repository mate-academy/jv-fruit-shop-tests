package core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ReportWriteExceptionTest {
    @Test
    public void testReportWriteException_Message_notOk() {
        String errorMessage = "Error occurred while writing the report.";
        ReportWriteException exception = new ReportWriteException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testReportWriteException_NullMessage_notOk() {
        ReportWriteException exception = new ReportWriteException(null);
        assertEquals(null, exception.getMessage());
    }
}
