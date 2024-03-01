package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    private static final String BREAK = System.lineSeparator();
    private static final String TEST_FILE_VALID = "src/test/resources/ConvTest0.csv";
    private static final String TEST_FILE_WRONG_PATH = "src/test/resources/wrong/ConvTest0.csv";
    private static CsvFileReader csvFileReader;

    @BeforeAll
    static void setUp() {
        csvFileReader = new CsvFileReader();
    }

    @Test
    void readFile_validPath_ok() {
        String input = "type,fruit,quantity" + BREAK
                + "b,banana,20" + BREAK
                + "b,apple,100" + BREAK
                + "s,banana,100" + BREAK
                + "p,banana,13" + BREAK
                + "r,apple,10" + BREAK
                + "p,apple,20";
        var expected = Arrays.asList(input.split(BREAK));
        var actual = csvFileReader.readFile(TEST_FILE_VALID);
        assertEquals(expected, actual);
    }

    @Test
    void readFile_invalidPath_notOK() {
        assertThrows(RuntimeException.class, () -> {
            csvFileReader.readFile(TEST_FILE_WRONG_PATH);
        });
    }
}
