package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.Fruit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private Map<Fruit, Integer> storage;
    private List<String> expectedReport;
    private List<String> actualReport;
    private ReportGenerator reportGenerator;

    @BeforeEach
    void init() {
        storage = new HashMap<>();
        expectedReport = new ArrayList<>();
        expectedReport.add("fruit,quantity");
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void generateReport() {
        storage.put(new Fruit("apple"), 100);
        storage.put(new Fruit("banana"), 200);
        actualReport = reportGenerator.generateReport(storage);
        expectedReport.add("apple,100");
        expectedReport.add("banana,200");
        assertTrue(actualReport.contains("apple,100"));
        assertTrue(actualReport.contains("banana,200"));
    }

    @Test
    void inputDataForReportIncorrect() {
        storage.put(new Fruit("apple"), -100);
        storage.put(new Fruit("banana"), -200);
        assertThrows(RuntimeException.class, () -> reportGenerator.generateReport(storage));
    }
}
