package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportCreatorTest {
    private static final Map<String, Integer> EMPTY_MAP = new HashMap<>();
    private static final Map<String, Integer> FULL_MAP = Map.of("apple", 2, "banana", 100);
    private static final ReportCreator REPORT_CREATOR = new ReportCreator();
    private static final String REGEX_PATTERN = "^(\\w+),(\\d+)$";

    @Test
    void emptyResult_isOk() {
        String expected = "";
        String actual = REPORT_CREATOR.createReport(EMPTY_MAP);
        assertEquals(expected, actual);
    }

    @Test
    void quantityStringsEqualsWithElements_isOk() {
        String[] lines = REPORT_CREATOR.createReport(FULL_MAP).split("\n");
        int expected = FULL_MAP.size();
        int actual = lines.length;
        assertEquals(expected, actual);
    }

    @Test
    void stringPattern_isOk() {
        String[] lines = REPORT_CREATOR.createReport(FULL_MAP).split("\n");
        assertTrue(lines[0].matches(REGEX_PATTERN));
    }
}
