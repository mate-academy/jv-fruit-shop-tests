package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ReaderServiceImplTest {
    private ReaderService readerService;
    private Path tempFile;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        readerService = new ReaderServiceImpl();
        tempFile = Files.createTempFile("testFile", ".txt");
    }

    @Test
    void fileIsExists() throws IOException {
        Path tempFile = tempDir.resolve("Fruit.txt");
        List<String> expectedFruitsData = List.of("apple,10", "strawberry,5", "banana,7");
        Files.write(tempFile, expectedFruitsData);

        List<String> actualFruitsData = readerService.readData(tempFile.toString());
        assertEquals(expectedFruitsData, actualFruitsData);
    }

    @Test
    void emptyFile_NotOk() throws IOException {
        Files.write(tempFile, List.of());

        List<String> actualLine = readerService.readData(tempFile.toString());
        assertTrue(actualLine.isEmpty());
    }

    @Test
    void fileNotFound_Ok() {
        String invalidFile = "non_Inappropriate_file.txt";
        Exception exception = assertThrows(RuntimeException.class,
                () -> readerService.readData(invalidFile));
        assertTrue(exception.getMessage().contains("Can't read file"));
    }

    @Test
    void fileWithEmptyLines_Ok() throws IOException {
        List<String> expectedFruitsData = List.of("apple,10", " ", "banana,7");
        Files.write(tempFile, expectedFruitsData);

        List<String> actualFruitsData = readerService.readData(tempFile.toString());
        assertEquals(expectedFruitsData, actualFruitsData);
    }
}
