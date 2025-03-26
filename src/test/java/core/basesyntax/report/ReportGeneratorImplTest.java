package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.operation.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void generateValidReport() {
        Storage.storage.put("banana", 150);
        Storage.storage.put("apple", 200);

        String report = reportGenerator.generateReport();
        String expectedReport = String.join(System.lineSeparator(),
                "fruit,quantity",
                "banana,150",
                "apple,200",
                "");

        assertEquals(expectedReport, report);
    }

    @Test
    void generateEmptyReport() {
        String report = reportGenerator.generateReport();
        assertEquals("fruit,quantity" + System.lineSeparator(), report);
    }

    @Test
    void generateReportWithSingleEntry() {
        Storage.storage.put("apple", 100);

        String report = reportGenerator.generateReport();
        String expectedReport = String.join(System.lineSeparator(),
                "fruit,quantity",
                "apple,100",
                "");

        assertEquals(expectedReport, report);
    }

    @Test
    void generateReportWithMultipleEntries() {
        Storage.storage.put("banana", 150);
        Storage.storage.put("apple", 200);
        Storage.storage.put("orange", 300);

        String report = reportGenerator.generateReport();
        String expectedReport = String.join(System.lineSeparator(),
                "fruit,quantity",
                "banana,150",
                "orange,300",
                "apple,200",
                "");

        assertEquals(expectedReport, report);
    }

    @Test
    void generateReportWithZeroQuantity() {
        Storage.storage.put("apple", 0);

        String report = reportGenerator.generateReport();
        String expectedReport = String.join(System.lineSeparator(),
                "fruit,quantity",
                "apple,0",
                "");

        assertEquals(expectedReport, report);
    }

    @Test
    void generateReportWithNegativeQuantity() {
        Storage.storage.put("apple", -50);

        String report = reportGenerator.generateReport();
        String expectedReport = String.join(System.lineSeparator(),
                "fruit,quantity",
                "apple,-50",
                "");

        assertEquals(expectedReport, report);
    }
}
