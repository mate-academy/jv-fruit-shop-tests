package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String COMA = ",";
    private ReportService reportService;
    private Map<String, Integer> fruitMap;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
        fruitMap = new HashMap<>();
    }

    @Test
    void name() {
        final String expected = "fruit,weight\n" + "banana,15\n" + "orange,44";
        fruitMap.put("banana", 15);
        fruitMap.put("orange", 44);
        StringBuilder builder = new StringBuilder("fruit,weight" + LINE_SEPARATOR);
        for (Map.Entry<String, Integer> entry : fruitMap.entrySet()) {
            builder.append(entry.getKey())
                    .append(COMA)
                    .append(entry.getValue())
                    .append(LINE_SEPARATOR);
        }
        String actual = builder.toString();
        assertNotEquals(expected, actual);
    }

    @Test
    void createReport_Ok() {
        final String report = reportService.createReport();
        assertFalse(report.isEmpty());
    }
}
