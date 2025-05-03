package core.basesyntax.filereader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvFileReaderImplTest {
    private static final List<String> EXPECTED_CONTENT = Arrays.asList(
            "type,fruit,quantity",
            "b,banana,20",
            "s,apple,100"
    );
    private static final String NONEXISTENT_FILE = "src/test/resources/nonexistent.csv";
    private static final String VALID_INPUT_FILE = "src/test/resources/input_data.csv";
    private CsvFileReader csvFileReader;

    @BeforeEach
    void setUp() {
        csvFileReader = new CsvFileReaderImpl();
    }

    @Test
    void read_validFile_returnsList() {
        List<String> actual = csvFileReader.read(VALID_INPUT_FILE);
        assertEquals(EXPECTED_CONTENT, actual, "File content doesn't match the expected output.");
    }

    @Test
    void read_fileNotFound_throwsException() {
        assertThrows(RuntimeException.class,
                () -> csvFileReader.read(NONEXISTENT_FILE),
                "Expected exception when reading a non-existent file.");
    }
}
