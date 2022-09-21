package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.service.ReportService;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CsvReportServiceImplTest {
    private StorageDao storageDao;
    private ReportService reportService;

    @Before
    public void before() {
        storageDao = new StorageDaoImpl();
        reportService = new CsvReportServiceImpl(storageDao);
    }

    @Test
    public void get_report_Ok() {
        storageDao.update("Lemon",100);
        String actual = reportService.createReport();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("Lemon,100");
        String expected = stringBuilder.toString();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void after() {
        Storage.storage.clear();
    }
}
