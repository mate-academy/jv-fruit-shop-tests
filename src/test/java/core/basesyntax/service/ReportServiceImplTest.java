package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.ReportService;
import service.impl.ReportServiceImpl;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void reportService_Ok() {
        Storage.fruits.put("banana", 10);
        String report = reportService.createReport();
        StringBuilder expectedReportBuilder =
                new StringBuilder("fruit,quantity" + System.lineSeparator());
        expectedReportBuilder.append("banana,10").append(System.lineSeparator());
        String expectedReport = expectedReportBuilder.toString();
        assertEquals(report, expectedReport);
    }

    @Test
    void reportService_reportWithEmptyStorage_NotOk() {
        Storage.fruits.clear();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reportService.createReport());
        assertEquals("Cant generate report, Storage is empty", exception.getMessage());
    }
}
