package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Test;

public class ReportServiceImplTest {

    @After
    public void afterEachTest() {
        Storage.getStorage().clear();
    }

    @Test
    public void createReport_Ok() {
        ReportService reportService = new ReportServiceImpl();
        StringBuilder expectedReportBuilder = new StringBuilder("fruit,quantity")
                .append(System.lineSeparator());
        String actualReport = reportService.generateReport();
        assertEquals(expectedReportBuilder.toString(), actualReport);

        Storage.getStorage().put(new Fruit("banana"), 5);
        expectedReportBuilder.append("banana,").append(5).append(System.lineSeparator());
        actualReport = reportService.generateReport();
        assertEquals(expectedReportBuilder.toString(), actualReport);

        Storage.getStorage().put(new Fruit("apple"), 10);
        expectedReportBuilder.append("apple,").append(10).append(System.lineSeparator());
        actualReport = reportService.generateReport();
        assertEquals(expectedReportBuilder.toString(), actualReport);
    }
}
