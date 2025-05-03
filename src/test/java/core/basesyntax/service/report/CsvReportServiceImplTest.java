package core.basesyntax.service.report;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CsvReportServiceImplTest {
    private StorageDao storageDao;
    private ReportService reportService;
    private String titleReport = "fruit,quantity";

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        reportService = new CsvReportServiceImpl(storageDao);
    }

    @Test
    public void reportEmptyData_Ok() {
        assertEquals(titleReport,reportService.createReport());
    }

    @Test
    public void reportWithData_Ok() {
        storageDao.update("banana",20);
        storageDao.update("apple",10);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(titleReport)
                .append(System.lineSeparator())
                .append("banana,20")
                .append(System.lineSeparator())
                .append("apple,10");
        assertEquals(stringBuilder.toString(),reportService.createReport());
    }

    @After
    public void tearDown() {
        storageDao.getAllFruitsFromStorage().clear();
    }
}
