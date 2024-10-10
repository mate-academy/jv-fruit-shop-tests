package core.basesyntax.service.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitRecord;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private ReportService reportService;
    private List<FruitRecord> fruitRecords;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
        fruitRecords = new ArrayList<>();
        Storage.storage.clear();
    }

    @Test
    void getReport_EmptyRecordList_ReturnsHeaderOnly() {
        String report = reportService.getReport(Collections.emptyList());
        assertEquals("fruit,quantity\n", report);
    }

    @Test
    void getReport_ValidRecords_ReturnsFormattedReport() {
        fruitRecords.add(new FruitRecord(FruitRecord.Operation.BALANCE, "apple", 50));
        fruitRecords.add(new FruitRecord(FruitRecord.Operation.SUPPLY, "banana", 30));
        fruitRecords.add(new FruitRecord(FruitRecord.Operation.PURCHASE, "apple", 10));

        String expectedReport = "fruit,quantity\n"
                + "apple,50\n"
                + "banana,30\n"
                + "apple,10\n";
        String actualReport = reportService.getReport(fruitRecords);

        assertEquals(expectedReport, actualReport,
                "Report should contain only the header when the record list is empty");
    }
}
