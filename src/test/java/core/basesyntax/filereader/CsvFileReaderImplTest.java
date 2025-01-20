package core.basesyntax.filereader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvFileReaderImplTest {
    private static final String NONEXISTENT_FILE = "nonexistent.csv";
    private static final String TEMP_FILE_PREFIX = "test_input.csv";
    private static final String TEMP_FILE_SUFFIX = ".csv";
    private static final List<String> EXPECTED_CONTENT = Arrays.asList(
            "type,fruit,quantity",
            "b,banana,20",
            "s,apple,100"
    );
    private CsvFileReader csvFileReader;

    @BeforeEach
    void setUp() {
        csvFileReader = new CsvFileReaderImpl();
    }

    @Test
    void read_validFile_returnsList() throws IOException {
        Path tempFile = Files.createTempFile(TEMP_FILE_PREFIX, TEMP_FILE_SUFFIX);
        Files.write(tempFile, EXPECTED_CONTENT);

        List<String> actual = csvFileReader.read(tempFile.toString());

        assertEquals(EXPECTED_CONTENT, actual, "File content doesn't match the expected output.");
        Files.deleteIfExists(tempFile);
    }

    @Test
    void read_fileNotFound_throwsException() {
        assertThrows(RuntimeException.class,
                () -> csvFileReader.read(NONEXISTENT_FILE),
                "Expected exception when reading a non-existent file.");
    }
}
