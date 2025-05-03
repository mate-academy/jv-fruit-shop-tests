package core.basesyntax.writereport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class WriteReportImplTest {

    private final WriteReport writeReport = new WriteReportImpl();

    @Test
    void writeToFile_validInput_fileContainsContent(@TempDir Path tempDir) throws IOException {
        String content = "Test content";
        Path filePath = tempDir.resolve("testFile.txt");
        writeReport.writeToFile(content, filePath.toString());
        assertEquals(content, readFileToString(filePath),
                "File content does not match the written content");
    }

    private String readFileToString(@TempDir Path filePath) throws IOException {
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            line = reader.readLine();
        }
        return line;
    }

    @Test
    void writeToFile_invalidPath_notOk() {
        String content = "Test data";
        String invalidPath = "/invalid/path/testFile.txt";

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> writeReport.writeToFile(content, invalidPath));

        assertEquals(exception.getMessage(), "Can't write to file: testFile.txt");
    }
}
