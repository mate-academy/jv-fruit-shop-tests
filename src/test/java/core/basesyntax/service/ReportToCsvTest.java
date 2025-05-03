package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.StockDao;
import core.basesyntax.db.StockDaoStorageImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReportToCsvTest {
    private final StockDao stockDao = new StockDaoStorageImpl();

    @Test
    void prepare_addingProductData_Ok() {
        final String inventoryFileForReportTest = "inventoryForReportTest.csv";
        Inventory inventoryForReportTest =
                new InventoryFromCsv(stockDao, inventoryFileForReportTest);
        inventoryForReportTest.synchronizeWithTheStorage();

        final String testReportFile = "testReport.csv";
        Report testReport = new ReportToCsv(stockDao, testReportFile);
        testReport.prepare();

        List<String> expectedReport = new ArrayList<>();
        String reportLineOne = "banana,20";
        String reportLineTwo = "apple,70";
        String reportLineThree = "lemon,30";
        expectedReport.add(reportLineOne);
        expectedReport.add(reportLineTwo);
        expectedReport.add(reportLineThree);

        try {
            List<String> lines = Files.readAllLines(Path.of(testReportFile));
            assertEquals(expectedReport, lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
