package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    private static final String TEST_FILE_0 = "src/test/resources/ConvTest0.csv";
    private static CsvFileReader csvFileReader;

    @Test
    void readFile_validPath_Ok() {
        csvFileReader = new CsvFileReader();
        String input = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13\n"
                + "r,apple,10\n"
                + "p,apple,20";
        var expected = Arrays.asList(input.split("\n"));
        var actual = csvFileReader.readFile(TEST_FILE_0);
        assertEquals(expected, actual);
    }
}
