package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;
    private static Map<String, Integer> dataMap;
    private String expected;
    private String actual;

    @BeforeAll
    static void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        dataMap = new HashMap<>();
        dataMap.put("banana",10);
        dataMap.put("avocado",12);
    }

    @Test
    void checkValidReport_Ok() {
        expected = "fruit,quantity"
                + System.lineSeparator() + "banana,10"
                + System.lineSeparator() + "avocado,12";
        actual = reportGenerator.generateReport(dataMap);
        assertEquals(expected, actual);
    }
}
