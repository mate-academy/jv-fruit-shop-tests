package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.report.ReportGeneratorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {

    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    public void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    public void getReport_emptyStorage_returnsHeaderOnly() {
        Map<String, Integer> storage = new HashMap<>();

        String report = reportGenerator.getReport(storage);

        assertEquals("fruit,quantity" + System.lineSeparator(), report);
    }

    @Test
    public void getReport_singleFruit_returnsCorrectReport() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 10);

        String report = reportGenerator.getReport(storage);

        assertEquals("fruit,quantity" + System.lineSeparator()
                + "apple,10" + System.lineSeparator(), report);
    }

    @Test
    void getReport_multipleFruits_returnsCorrectReport() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 10);
        storage.put("banana", 5);

        String expectedReport = "fruit,quantity"
                + System.lineSeparator()
                + "apple,10"
                + System.lineSeparator()
                + "banana,5" + System.lineSeparator();

        String actualReport = reportGenerator.getReport(storage);

        System.out.println("Expected report: [" + expectedReport + "]");
        System.out.println("Actual report: [" + actualReport + "]");

        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void getReport_fruitsWithZeroQuantity_returnsCorrectReport() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("kiwi", 0);
        storage.put("grape", 0);

        String report = reportGenerator.getReport(storage);

        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "grape,0" + System.lineSeparator()
                + "kiwi,0" + System.lineSeparator();

        assertEquals(expectedReport.trim(), report.trim());
    }
}
