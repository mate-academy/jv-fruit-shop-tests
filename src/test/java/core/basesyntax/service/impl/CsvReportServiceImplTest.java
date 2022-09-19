package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.service.ReportService;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class CsvReportServiceImplTest {
    private StorageDao storageDao = new StorageDaoImpl();

    @Test
    public void getReport_Ok() {
        storageDao.update("Lemon",100);
        ReportService reportService = new CsvReportServiceImpl(storageDao);
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
