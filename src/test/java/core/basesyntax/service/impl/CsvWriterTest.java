package core.basesyntax.service.impl;

import core.basesyntax.exception.CsvFileException;
import core.basesyntax.exception.CsvIllegalArgumentException;
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
    public void writeToFile_NullPath_ThrowsCsvIllegalArgumentException() {
        Assertions.assertThrows(CsvIllegalArgumentException.class, () ->
                        csvWriter.writeToFile(null, TEST_DATA),
                "Path and data must not be null");
    }

    @Test
    public void writeToFile_NullData_ThrowsCsvIllegalArgumentException(@TempDir Path tempDir) {
        Path filePath = tempDir.resolve(TEST_FILE_NAME);
        Assertions.assertThrows(CsvIllegalArgumentException.class, () ->
                        csvWriter.writeToFile(filePath, null),
                "Path and data must not be null");
    }

    @Test
    public void writeToFile_WriteToDirectoryPath_ThrowsCsvFileException(
            @TempDir Path tempDir) {
        Assertions.assertThrows(
                CsvFileException.class,
                () -> csvWriter.writeToFile(tempDir, TEST_DATA),
                "Cannot write to CSV file in directory path" + tempDir
        );
    }
}
