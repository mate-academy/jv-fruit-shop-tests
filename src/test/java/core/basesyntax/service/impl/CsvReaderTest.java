package core.basesyntax.service.impl;

import core.basesyntax.exception.CsvFileException;
import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class CsvReaderTest {
    private static final String TEST_FILE_NAME = "test.csv";
    private static final String NON_EXISTENT_FILE_NAME = "non-existent-file.csv";
    private static final String EMPTY_FILE_NAME = "empty.csv";
    private static final List<String> TEST_DATA = Arrays.asList(
            "operation,fruit,quantity",
            "b,apple,10",
            "s,banana,20",
            "p,orange,5"
    );
    private static FileReader csvReader;

    @BeforeAll
    static void beforeAll() {
        csvReader = new CsvReader();
    }

    @Test
    public void readFromFile_ValidPath_ReturnsFileContents(
            @TempDir Path tempDir) throws IOException {
        Path filePath = tempDir.resolve(TEST_FILE_NAME);
        Files.write(filePath, TEST_DATA);
        List<String> actualData = csvReader.readFromFile(filePath);
        Assertions.assertEquals(TEST_DATA, actualData);
    }

    @Test
    public void readFromFile_NonExistentPath_ThrowsCsvFileException() {
        Path nonExistentPath = Path.of(NON_EXISTENT_FILE_NAME);
        Assertions.assertThrows(CsvFileException.class, () -> {
            csvReader.readFromFile(nonExistentPath);
        });
    }

    @Test
    public void readFromFile_EmptyFile_ReturnsEmptyList(@TempDir Path tempDir) throws IOException {
        Path emptyFilePath = tempDir.resolve(EMPTY_FILE_NAME);
        Files.createFile(emptyFilePath);
        List<String> actualData = csvReader.readFromFile(emptyFilePath);
        Assertions.assertTrue(actualData.isEmpty());
    }

    @Test
    public void readFromFile_DirectoryPath_ThrowsCsvFileException(@TempDir Path tempDir) {
        Assertions.assertThrows(CsvFileException.class, () -> {
            csvReader.readFromFile(tempDir);
        });
    }
}
