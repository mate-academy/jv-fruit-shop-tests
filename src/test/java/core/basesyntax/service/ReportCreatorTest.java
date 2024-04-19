package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportCreatorTest {
    private final Map<String, Integer> emptyMap = new HashMap<>();
    private final Map<String, Integer> fullMap = Map.of("apple", 2, "banana", 100);
    private final ReportCreator reportCreator = new ReportCreator();
    private final String regexPattern = "^(\\w+),(\\d+)$";

    @Test
    void emptyResult_isOk() {
        String expected = "";
        String actual = reportCreator.createReport(emptyMap);
        assertEquals(expected, actual);
    }

    @Test
    void quantityStringsEqualsQuantityElements_isOk() {
        String[] lines = reportCreator.createReport(fullMap).split("\n");
        int expected = fullMap.size();
        int actual = lines.length;
        assertEquals(expected, actual);
    }

    @Test
    void stringPattern_isOk() {
        String[] lines = reportCreator.createReport(fullMap).split("\n");
        assertTrue(lines[0].matches(regexPattern));
    }
}
