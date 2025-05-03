package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.CsvFileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvFileReaderTest {
    private static final String FILE_LOCATION = "src/main/resources/databaseOfFruits.csv";
    private static final String WRONG_FILE_LOCATION = "src/main/resources/DdatabaseOfFruits.csv";
    private static FileReader fileReader;
    private static List<String> expected;

    @BeforeAll
    static void beforeAll() {
        fileReader = new CsvFileReader();
        expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("    b,banana,20");
        expected.add("    b,apple,100");
        expected.add("    s,banana,100");
        expected.add("    p,banana,13");
        expected.add("    r,apple,10");
        expected.add("    p,apple,20");
        expected.add("    p,banana,5");
        expected.add("    s,banana,50");
    }

    @Test
    void readDataFrom_correctFilePath_ok() {

        List<String> actual = fileReader.readDataFrom(FILE_LOCATION);
        assertEquals(expected, actual);
    }

    @Test
    void readDataFrom_incorrectFilePath_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.readDataFrom(WRONG_FILE_LOCATION));
    }
}
