package core.basesyntax.service;

import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl(new StorageDaoImpl());
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
    }

    @Test
    public void makeReportNormalDate_ok() {
        String expected = "fruit,quantity".concat(System.lineSeparator())
                + "banana,152".concat(System.lineSeparator())
                + "apple,90".concat(System.lineSeparator());
        String actual = reportService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void makeReportEmptyStorage_ok() {
        Storage.fruits.clear();
        String actual = reportService.createReport();
        String expected = "fruit,quantity".concat(System.lineSeparator());
        Assert.assertEquals(expected,actual);
    }
}
