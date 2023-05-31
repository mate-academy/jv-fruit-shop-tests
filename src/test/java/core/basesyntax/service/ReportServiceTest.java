package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.After;
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

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void createReport_FromStorage_Ok() {
        Storage.fruitStorage.put("banana", 100);
        String expected = HEADER + System.lineSeparator() + "banana" + DELIMITER + 100;
        String actual = reportService.createReport();
        Assert.assertEquals(expected, actual);
    }
}
