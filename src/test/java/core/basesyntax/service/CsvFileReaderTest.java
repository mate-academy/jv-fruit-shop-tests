package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvFileReaderTest {
    private static final String VALID_INPUT_FILE_PATH = "src/test/resources/inputData.csv";
    private static final String INVALID_INPUT_FILE_PATH = "src/test/resources/invalidinput.csv";
    private static FileReader fileReader;
    private static final List<String> EXPECTED_DATA = List.of(
            "type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50"
    );

    @BeforeAll
    static void beforeAll() {
        fileReader = new CsvFileReader();
    }

    @Test
    void read_NotExistingFile_NotOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read(INVALID_INPUT_FILE_PATH));
    }

    @Test
    void read_CorrectFile_Ok() {
        List<String> actualData = fileReader.read(VALID_INPUT_FILE_PATH);
        assertEquals(EXPECTED_DATA, actualData);
    }
}

