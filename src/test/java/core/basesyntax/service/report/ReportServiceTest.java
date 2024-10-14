package core.basesyntax.service.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
        Storage.storage.clear();
    }

    @Test
    void getReport_EmptyRecordList_ReturnsHeaderOnly() {
        String report = reportService.getReport();
        assertEquals("fruit,quantity\n", report);
    }

    @Test
    void getReport_ValidRecords_ReturnsFormattedReport() {
        Storage.storage.put("apple", 50);
        Storage.storage.put("banana", 30);
        Storage.storage.put("apple", 10);

        String expectedReport = "fruit,quantity\n"
                + "banana,30\n"
                + "apple,10\n";
        String actualReport = reportService.getReport();

        assertEquals(expectedReport, actualReport,
                "Report should contain only the header when the record list is empty");
    }
}
