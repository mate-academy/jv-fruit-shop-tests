package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private FileWriterServiceImpl fileWriterService;

    @BeforeEach
    void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    void writeFile_validData_ok() throws IOException {
        String report = "This is a test report.";
        File tempFile = File.createTempFile("test-report", ".csv");

        fileWriterService.writeFile(report, tempFile.getAbsolutePath());

        String writtenContent = readFileContent(tempFile);
        assertEquals(report, writtenContent);

        tempFile.deleteOnExit();
    }

    @Test
    void writeFile_emptyData_ok() throws IOException {
        String report = "";
        File tempFile = File.createTempFile("empty-report", ".csv");

        fileWriterService.writeFile(report, tempFile.getAbsolutePath());

        String writtenContent = readFileContent(tempFile);
        assertEquals(report, writtenContent);

        tempFile.deleteOnExit();
    }

    @Test
    void writeFile_invalidPath_notOk() {
        String report = "This is a test report.";
        String invalidPath = "/invalid/path/test-report.csv";

        Exception exception = assertThrows(RuntimeException.class, () ->
                fileWriterService.writeFile(report, invalidPath));

        assertEquals("Can't write a " + invalidPath, exception.getMessage());
    }

    private String readFileContent(File file) throws IOException {
        return Files.readString(file.toPath());
    }
}
