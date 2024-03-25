package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportCreator;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private static ReportCreator reportCreator;
    private String expected = """
            fruit,quantity
            apple,50
            banana,20
            """;

    @BeforeAll
    static void beforeAll() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    void createReport_ok() {
        Map<String, Integer> map = new TreeMap<>();
        map.put("banana", 20);
        map.put("apple", 50);

        String actual = reportCreator.createReport(map);
        assertEquals(expected, actual);
    }
}
