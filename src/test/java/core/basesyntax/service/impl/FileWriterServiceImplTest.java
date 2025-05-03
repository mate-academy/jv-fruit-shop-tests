package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileWriterServiceImplTest {
    private final FileWriterService fileWriterService = new FileWriterServiceImpl();

    @Test
    public void testWriteToFile_ok(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("testReport.csv");
        String expectedContent = "fruit,quantity\napple,50\nbanana,20";

        fileWriterService.writeToFile(testFile.toString(), expectedContent);

        assertTrue(Files.exists(testFile), "File should exist after writing");
        String actualContent = Files.readString(testFile);
        assertEquals(expectedContent, actualContent, "File content should match expected content");
    }

    @Test
    public void testWriteToFile_throwsExceptionOnInvalidPath_notOk() {
        String invalidPath = "/invalid_directory/report.txt";
        String reportContent = "Test report content";

        assertThrows(RuntimeException.class, () -> {
            fileWriterService.writeToFile(invalidPath, reportContent);
        });
    }
}
