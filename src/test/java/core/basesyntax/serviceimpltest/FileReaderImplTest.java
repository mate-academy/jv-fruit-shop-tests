package core.basesyntax.serviceimpltest;

import core.basesyntax.service.FileReader;
import core.basesyntax.serviceimpl.FileReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {

    private Path tempFile;

    @AfterEach
    void cleanup() throws IOException {
        if (tempFile != null && Files.exists(tempFile)) {
            Files.delete(tempFile);
        }
    }

    @Test
    void read_existingFile_shouldReturnLines() throws IOException {
        tempFile = Files.createTempFile("testFile", ".txt");
        List<String> expectedLines = Arrays.asList("apple,10", "banana,5", "orange,7");
        Files.write(tempFile, expectedLines);
        FileReader reader = new FileReaderImpl();
        List<String> actualLines = reader.read(tempFile.toString());
        Assertions.assertEquals(expectedLines, actualLines);
    }

    @Test
    void read_nonExistingFile_shouldThrowException() {
        FileReader reader = new FileReaderImpl();
        String nonExistingPath = "nonexistent_file.txt";
        Assertions.assertThrows(RuntimeException.class,
                () -> reader.read(nonExistingPath));
    }
}
