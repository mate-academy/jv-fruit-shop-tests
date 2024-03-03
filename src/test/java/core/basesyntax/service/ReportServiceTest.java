package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.FruitDatabase;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static final String REPORT_HEADER = "fruit, quantity" + System.lineSeparator();
    private static ReportService reportService;

    @BeforeAll
    public static void initReportService() {
        reportService = new ReportServiceImpl();
    }

    @AfterEach
    public void cleanDatabase() {
        FruitDatabase.database.clear();
    }

    @Test
    public void generateReport_emptyReport_ok() {
        String actualReport = reportService.generateReport();
        assertEquals(REPORT_HEADER, actualReport);
    }

    @Test
    public void generateReport_normalReport_ok() {
        String bananaName = "Banana";
        int bananaQuantity = 120;
        String appleName = "Name";
        int appleQuantity = 10;
        FruitDatabase.database.put(bananaName, bananaQuantity);
        FruitDatabase.database.put(appleName, appleQuantity);
        String actualReport = reportService.generateReport();
        assertTrue(actualReport.contains(REPORT_HEADER));
        assertTrue(actualReport.contains(bananaName)
                && actualReport.contains(Integer.toString(bananaQuantity)));
        assertTrue(actualReport.contains(appleName)
                && actualReport.contains(Integer.toString(appleQuantity)));
    }
}
