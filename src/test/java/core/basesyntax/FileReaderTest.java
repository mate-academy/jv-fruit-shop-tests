package core.basesyntax;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.CsvFileReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    private final FileReader fileReader = new CsvFileReaderImpl();
    private final String testFolderPath = "src/test/resources/";

    @BeforeEach
    void setUp() {
        List<String> lines = Arrays.asList("Line 1", "Line 2", "Line 3");
        String fileName = testFolderPath + "test.csv";
        try {
            Files.write(Path.of(fileName), lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to set up test data: " + fileName, e);
        }
    }

    @Test
    void readFromFile_ValidFile_Ok() {
        String fileName = testFolderPath + "test.csv";
        List<String> content = fileReader.readFromFile(fileName);
        List<String> expectedContent = Arrays.asList("Line 1", "Line 2", "Line 3");
        Assertions.assertEquals(expectedContent, content);
    }

    @Test
    void readFromFile_NonExistentFile_NotOk() {
        String nonExistentFileName = "non_existent_file.csv";
        Assertions.assertThrows(RuntimeException.class,
                () -> fileReader.readFromFile(nonExistentFileName));
    }
}
