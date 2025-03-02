package core.basesyntax;

import core.basesyntax.impl.FileWriterImpl;
import core.basesyntax.service.FileWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class FileWriterImplTest {
    private FileWriter fileWriter;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        fileWriter = new FileWriterImpl();
        tempFile = Files.createTempFile("testReport", ".txt");
    }

    @Test
    void writeReport_ValidData_WritesSuccessfully() throws IOException {
        String content = "fruit,quantity\nbanana,10\napple,20";
        fileWriter.writeReport(content, tempFile.toString());

        String result = Files.readString(tempFile);
        assertEquals(content, result);
    }

    @Test
    void writeReport_EmptyData_WritesEmptyFile() throws IOException {
        fileWriter.writeReport("", tempFile.toString());

        String result = Files.readString(tempFile);
        assertTrue(result.isEmpty());
    }

    @Test
    void writeReport_InvalidPath_ThrowsRuntimeException() {
        String invalidPath = "/invalid/path/report.txt";

        Exception exception = assertThrows(RuntimeException.class, () ->
                fileWriter.writeReport("test data", invalidPath)
        );

        assertTrue(exception.getMessage().contains("Failed to write report to file"));
    }
}
