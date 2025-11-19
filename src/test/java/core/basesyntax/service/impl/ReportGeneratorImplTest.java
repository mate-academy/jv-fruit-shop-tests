package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private final ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();

    @BeforeEach
    void resetStorage() {
        Storage.clear();
    }

    @Test
    void positiveReportGenerator() {
        Storage.put("banana", 10);
        Storage.put("apple", 5);

        String report = reportGenerator.getReport();

        String[] lines = report.split(System.lineSeparator());
        List<String> actual = List.of(lines);

        List<String> expected = List.of("banana,10", "apple,5");

        assertEquals(expected, actual);
    }

    @Test
    void emptyReportGenerator() {
        String report = reportGenerator.getReport();
        assertEquals("", report);
    }
}
