package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.services.ReportGeneratorService;
import core.basesyntax.services.impl.ReportGeneratorServiceImpl;
import org.junit.jupiter.api.Test;

public class ReportGeneratorServiceTest {

    @Test
    void generateReportWithData() {
        Storage storage = new Storage();
        storage.updateInventory("apple", 100);
        storage.updateInventory("orange", 200);
        ReportGeneratorService reportService = new ReportGeneratorServiceImpl(storage);
        String expectedReport = "fruit, quantity\naapple,100\norange,200";
        String actualReport = reportService.generateReport();
        assertEquals(expectedReport, actualReport);
    }
}
