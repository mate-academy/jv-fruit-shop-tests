package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_validStorage_success() {
        Map<String, Integer> storage = Map.of(
                "apple", 50,
                "banana", 30,
                "orange", 20
        );

        String expectedReport =
                "fruit,quantity" + System.lineSeparator()
                        + "apple,50" + System.lineSeparator()
                        + "banana,30" + System.lineSeparator()
                        + "orange,20" + System.lineSeparator();

        String actualReport = reportGenerator.getReport(storage);

        assertEquals(expectedReport, actualReport,
                "The generated report should match the expected format.");
    }

    @Test
    void getReport_emptyStorage_success() {
        Map<String, Integer> storage = Map.of();

        String expectedReport = "fruit,quantity" + System.lineSeparator();
        String actualReport = reportGenerator.getReport(storage);

        assertEquals(expectedReport, actualReport,
                "The report for an empty storage should contain only the header.");
    }
}
