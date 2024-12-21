package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        File tempFile = File.createTempFile("test-report", ".txt");

        fileWriterService.writeFile(report, tempFile.getAbsolutePath());

        String writtenContent = Files.readString(tempFile.toPath());
        assertEquals(report, writtenContent);

        tempFile.deleteOnExit();
    }

    @Test
    void writeFile_emptyData_ok() throws IOException {
        String report = "";
        File tempFile = File.createTempFile("empty-report", ".txt");

        fileWriterService.writeFile(report, tempFile.getAbsolutePath());

        String writtenContent = Files.readString(tempFile.toPath());
        assertEquals(report, writtenContent);

        tempFile.deleteOnExit();
    }

    @Test
    void writeFile_invalidPath_notOk() {
        String report = "This is a test report.";
        String invalidPath = "/invalid/path/test-report.txt";

        Exception exception = assertThrows(RuntimeException.class, () ->
                fileWriterService.writeFile(report, invalidPath));
        assertTrue(exception.getMessage().contains("Can't write a"));
    }
}
