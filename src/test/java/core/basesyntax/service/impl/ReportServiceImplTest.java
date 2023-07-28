package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.impl.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private ReportService reportService = new ReportServiceImpl();

    @AfterEach
    void tearDown() {
        FruitStorage.FRUITS.clear();
    }

    @Test
    void getReport_correctReportData_ok() {
        FruitStorage.FRUITS.put("banana", 152);
        FruitStorage.FRUITS.put("apple", 90);
        String actual = reportService.getReport();
        String expected = new StringBuilder().append("banana,152").append(System.lineSeparator())
                .append("apple,90").append(System.lineSeparator()).toString();
        assertEquals(expected, actual);
    }
}
