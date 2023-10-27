package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import core.basesyntax.service.FileParser;
import core.basesyntax.service.impl.CsvFileParserImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvFileParserImplTest {

    private static FileParser fileParser;

    @BeforeAll
    static void setUp() {
        fileParser = new CsvFileParserImpl();
    }

    @Test
    void parse_ok() {
        String data = "r,apple,123";

        String[] expected = {"r", "apple", "123"};

        String[] actual = fileParser.parse(data);

        assertArrayEquals(expected, actual, "Returned array must be equals with the expected!");
    }

}
