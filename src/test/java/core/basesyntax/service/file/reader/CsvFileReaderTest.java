package core.basesyntax.service.file.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    private static final String VALID_DATA_FILE_PATH = "resources/data.csv";
    private static final String INVALID_DATA_FILE_PATH = "invalid path";
    private static FileReader csvFileReader;

    @BeforeAll
    static void beforeAll() {
        csvFileReader = new CsvFileReader();
    }

    @Test
    void readFromFile_validPath_ok() {
        List<String> actual = csvFileReader.readFromFile(VALID_DATA_FILE_PATH);
        List<String> expected = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
        assertEquals(expected, actual);
    }

    @Test
    void readFromFile_invalidPath_notOk() {
        assertThrows(RuntimeException.class,
                () -> csvFileReader.readFromFile(INVALID_DATA_FILE_PATH));
    }
}
