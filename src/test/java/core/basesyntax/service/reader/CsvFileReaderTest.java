package core.basesyntax.service.reader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvFileReaderTest {
    private static final String VALID_PATH_READ = "fruit1.csv";
    private static final String NOT_VALID_PATH_READ = "nonexistent.csv";
    FileReader csvFileReader;

    @BeforeEach
    void setUp() {
        csvFileReader = new CsvFileReader();
    }

    @Test
    void readFile_worksNotProperlyThrowsException_notOk() {
        assertThrows(RuntimeException.class, () -> {
            csvFileReader.readFile(NOT_VALID_PATH_READ);
        });
    }

    @Test
    void readFile_worksProperly_ok() {
        List<String> actual = csvFileReader.readFile(VALID_PATH_READ);
        List<String> expected = List.of(
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
        assertEquals(expected, actual);
    }
}