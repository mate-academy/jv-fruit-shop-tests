package core.basesyntax.service.reportservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_validData_ok() {
        Storage.fruits.put("apple", 10);
        Storage.fruits.put("banana", 20);
        List<String> expectedReportLines = Stream.of("fruit,quantity", "apple,10", "banana,20")
                .sorted().toList();
        List<String> actualReportLines = Stream.of(reportGenerator.getReport()
                        .split(System.lineSeparator()))
                .sorted().toList();
        assertEquals(expectedReportLines, actualReportLines);
    }

    @Test
    void getReport_emptyStorage_ok() {
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        assertEquals(expectedReport, reportGenerator.getReport());
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
