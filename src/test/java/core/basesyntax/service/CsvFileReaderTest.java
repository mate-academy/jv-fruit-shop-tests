package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    private static final String BREAK = System.lineSeparator();
    private static final String TEST_FILE_0 = "src/test/resources/ConvTest0.csv";
    private static CsvFileReader csvFileReader;

    @Test
    void readFile_validPath_Ok() {
        csvFileReader = new CsvFileReader();
        String input = "type,fruit,quantity" + BREAK
                + "b,banana,20" + BREAK
                + "b,apple,100" + BREAK
                + "s,banana,100" + BREAK
                + "p,banana,13" + BREAK
                + "r,apple,10" + BREAK
                + "p,apple,20";
        var expected = Arrays.asList(input.split(BREAK));
        var actual = csvFileReader.readFile(TEST_FILE_0);
        assertEquals(expected, actual);
    }
}
