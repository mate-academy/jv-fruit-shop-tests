package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        storage = new HashMap<>();
    }

    @Test
    void getReport_validStorage_success() {
        storage.put("apple", 50);
        storage.put("banana", 30);
        storage.put("orange", 20);

        String expectedReport = "fruit,quantity"
                + System.lineSeparator()
                + "apple,50"
                + System.lineSeparator()
                + "banana,30"
                + System.lineSeparator()
                + "orange,20"
                + System.lineSeparator();

        String actualReport = reportGenerator.getReport(storage);

        assertEquals(expectedReport, actualReport, "Отчет должен совпадать с ожидаемым.");
    }
}
