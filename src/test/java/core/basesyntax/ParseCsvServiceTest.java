package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exception.ValidationDataException;
import core.basesyntax.services.ParseCsvService;
import core.basesyntax.services.impl.ParseCsvServiceImpl;
import core.basesyntax.util.ConstantsForCsvParse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ParseCsvServiceTest {
    private static ParseCsvService parseCsvServiceTest;
    private static String[] defaultCorrectData;
    private static List<String[]> defaultCorrectParseData;

    @BeforeAll
    static void createParseDefaultData() {
        parseCsvServiceTest = new ParseCsvServiceImpl();
        defaultCorrectData = new String[] {
                "b,banana,120",
                "b,apple,130",
                "r,banana,40",
                "s,banana,20",
                "p,apple,50",
                "p,banana,40"
        };
        defaultCorrectParseData = new ArrayList<>();
        for (String str : defaultCorrectData) {
            defaultCorrectParseData.add(str.split(ConstantsForCsvParse.COMMA));
        }
    }

    @Test
    void parse_isNullData_notOk() {
        assertThrows(ValidationDataException.class,
                () -> parseCsvServiceTest.parse(null));
    }

    @Test
    void parse_isEmptyData_notOk() {
        assertThrows(ValidationDataException.class,
                () -> parseCsvServiceTest.parse(new String[0]));
    }

    @Test
    void parse_isValidData_ok() {
        List<String[]> actual = parseCsvServiceTest.parse(defaultCorrectData);
        List<String[]> expected = defaultCorrectParseData;
        assertTrue(Arrays.deepEquals(expected.toArray(), actual.toArray()));
    }
}
