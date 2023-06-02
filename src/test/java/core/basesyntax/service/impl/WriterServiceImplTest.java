package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class WriterServiceImplTest {
    private WriterServiceImpl writerService;
    private Path filePath;

    @BeforeEach
    void setUp(@TempDir Path tempDir) {
        filePath = tempDir.resolve("test.txt");
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeDataToFile_existingFile_ok() throws IOException {
        List<String> data = List.of("Line 1", "Line 2", "Line 3");
        writerService.writeDataToFile(filePath.toString(), data);
        List<String> result = Files.readAllLines(filePath);
        assertEquals(data, result);
    }

    @Test
    void writeDataToFile_invalidPath_notOk() {
        List<String> data = List.of("Line 1", "Line 2", "Line 3");
        String invalidPath = "\0";
        String invalidFilePath = invalidPath + filePath.toString();
        assertThrows(RuntimeException.class,
                () -> writerService.writeDataToFile(invalidFilePath, data));
    }

    @Test
    void writeDataToFile_readOnlyFile_notOk() throws IOException {
        Files.createFile(filePath);
        Files.setAttribute(filePath, "dos:readonly", true);

        List<String> data = List.of("Line 1", "Line 2", "Line 3");
        assertThrows(RuntimeException.class,
                () -> writerService.writeDataToFile(filePath.toString(), data));
    }
}
