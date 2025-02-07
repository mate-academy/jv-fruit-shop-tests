package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.data.Storage;
import core.basesyntax.service.ReportGenerator;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String REPORT_HEADER = "Fruit   Quantity";
    private static final String REPORT_SEPARATOR = " - ";
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void getReport_validData_success() {
        Storage.add("apple", 10);
        Storage.add("banana", 20);

        String actualReport = reportGenerator.getReport();
        List<String> actualLines = Arrays.asList(actualReport.split(System.lineSeparator()));
        List<String> expectedLines = Arrays.asList(
                REPORT_HEADER,
                "apple" + REPORT_SEPARATOR + "10",
                "banana" + REPORT_SEPARATOR + "20"
        );

        Assertions.assertTrue(actualLines.containsAll(expectedLines)
                        && expectedLines.containsAll(actualLines),
                "Report content mismatch!");
    }

    @Test
    void getReport_emptyStorage_returnsOnlyHeader() {
        String expectedReport = REPORT_HEADER + System.lineSeparator();
        assertEquals(expectedReport, reportGenerator.getReport());
    }
}
