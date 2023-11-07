package core.basesyntax.service.reader;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    private static FileReader fileReader;
    private static final String VALID_PATH = "src/main/resources/input.csv";
    private static final String INVALID_PATH = "in/valid/path/input.csv";
    private static final String EMPTY_PATH = "";
    private static final List<String> VALID_LIST = List.of(
            "type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");

    @BeforeEach
    void setUp() {
        fileReader = new CsvFileReader();
    }

    @Test
    void readFileWithInvalidPath_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileReader.read(INVALID_PATH));
    }

    @Test
    void readFileWithEmptyPath_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileReader.read(EMPTY_PATH));
    }

    @Test
    void readFileWithNullPath_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileReader.read(null));
    }

    @Test
    void readFile_worksProperly_ok() {
        List<String> actual = fileReader.read(VALID_PATH);
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
        Assertions.assertEquals(expected, actual);
    }
}
