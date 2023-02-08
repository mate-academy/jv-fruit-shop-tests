package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static final String HEADER = "fruit,quantity";
    private static final String DELIMITER = ",";
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        Storage.fruitStorage.clear();
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReportFromStorage_Ok() {
        Storage.fruitStorage.put("banana", 100);
        String expected = HEADER + System.lineSeparator() + "banana" + DELIMITER + 100;
        String actual = reportService.createReport();
        Assert.assertEquals(expected, actual);
    }
}
