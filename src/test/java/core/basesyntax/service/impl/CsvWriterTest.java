package core.basesyntax.service.impl;

import core.basesyntax.exception.CsvFileException;
import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class CsvWriterTest {
    private static final String TEST_FILE_NAME = "test.csv";
    private static final String DIRECTORY_NAME = "directory";
    private static final List<String> TEST_DATA = Arrays.asList(
            "operation,fruit,quantity",
            "b,apple,10",
            "s,banana,20",
            "p,orange,5"
    );
    private static FileWriter csvWriter;

    @BeforeAll
    static void beforeAll() {
        csvWriter = new CsvWriter();
    }

    @Test
    public void writeToFile_ValidData_FileContainsCorrectData(
            @TempDir Path tempDir) throws IOException {
        Path filePath = tempDir.resolve(TEST_FILE_NAME);
        csvWriter.writeToFile(filePath, TEST_DATA);
        List<String> actualData = Files.readAllLines(filePath);
        Assertions.assertEquals(TEST_DATA, actualData);
    }

    @Test
    public void writeToFile_NullPath_ThrowsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            csvWriter.writeToFile(null, TEST_DATA);
        });
    }

    @Test
    public void writeToFile_NullData_ThrowsNullPointerException(@TempDir Path tempDir) {
        Path filePath = tempDir.resolve(TEST_FILE_NAME);
        Assertions.assertThrows(NullPointerException.class, () -> {
            csvWriter.writeToFile(filePath, null);
        });
    }

    @Test
    public void writeToFile_WriteToDirectoryPath_ThrowsCsvFileException(
            @TempDir Path tempDir) throws IOException {
        Path directoryPath = tempDir.resolve(DIRECTORY_NAME);
        Files.createDirectory(directoryPath);
        Assertions.assertThrows(CsvFileException.class, () -> {
            csvWriter.writeToFile(directoryPath, TEST_DATA);
        });
    }
}
