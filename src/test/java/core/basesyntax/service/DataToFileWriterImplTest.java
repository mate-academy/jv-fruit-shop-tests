package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class DataToFileWriterImplTest {

    @Test
    void writeData_Ok() throws IOException {
        String report = "This is a test report";
        String fileName = "testFile.txt";
        DataToFileWriter writer = new DataToFileWriterImpl();
        try {
            writer.writeData(report, fileName);
            File file = new File(fileName);
            assertTrue(file.exists(), "File should be created by writeData method");
            String fileContent = Files.readString(Paths.get(fileName));
            assertEquals(report, fileContent, "The file content should match the report");
        } finally {
            Files.deleteIfExists(Paths.get(fileName));
        }
    }

    @Test
    void writeData_NotOk() {
        String report = "This report won't be written";
        String fileName = "/invalid/path/testFile.txt";
        DataToFileWriter writer = new DataToFileWriterImpl();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> writer.writeData(report, fileName));
        assertTrue(exception.getMessage()
                        .contains("Error writing to file: /invalid/path/testFile.txt"),
                "Exception message should contain the file name");
    }
}
