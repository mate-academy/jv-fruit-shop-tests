package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.service.interfaces.TransactionParser;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportParserImplTest {
    private static final String COMMA_DIVIDER = ",";
    private static final String REPORT_HEADING = "fruit,quantity";

    private TransactionParser<String, Map<String, Integer>> reportParser;

    @BeforeEach
    void setUp() {
        reportParser = new ReportParserImpl();
    }

    @AfterEach
    void cleanUp() {
        Storage.clear();
    }

    @Test
    void parseReport_OK() {
        Storage.addPair("banana", 165);
        Storage.addPair("apple", 90);
        String expected = REPORT_HEADING + System.lineSeparator()
                + "banana" + COMMA_DIVIDER + "165" + System.lineSeparator()
                + "apple" + COMMA_DIVIDER + "90";
        String actual = reportParser.parse(Storage.getAll());
        Assertions.assertEquals(expected, actual, "Parser doesn't parse data correctly!");
    }

    @Test
    void parseReport_nullData_NotOk() {
        InvalidDataException actual = Assertions.assertThrows(InvalidDataException.class, () -> {
            reportParser.parse(null);
        });
        String expected = "Data for parsing report must not be null!";
        Assertions.assertEquals(expected, actual.getMessage());
    }
}
