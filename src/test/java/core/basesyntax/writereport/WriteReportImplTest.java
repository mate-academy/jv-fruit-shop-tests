package core.basesyntax.writereport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class WriteReportImplTest {

    private final WriteReportImpl writeReport = new WriteReportImpl();

    @Test
    void writeToFile_validInput_fileContainsContent(@TempDir Path tempDir) throws IOException {
        String content = "Test content";
        Path filePath = tempDir.resolve("testFile.txt");
        writeReport.writeToFile(content, filePath.toString());
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line = reader.readLine();
            assertEquals(content, line, "File content does not match the written content");
        }
    }

    @Test
    void writeToFile_invalidPath_throwsRuntimeException() {
        String content = "Test data";
        String invalidPath = "/invalid/path/testFile.txt";

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> writeReport.writeToFile(content, invalidPath));

        assertTrue(exception.getMessage().contains("Can't write to file"));
    }
}
