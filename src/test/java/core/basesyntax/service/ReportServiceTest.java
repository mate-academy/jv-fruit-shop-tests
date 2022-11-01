package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageDao;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static final String EMPTY_LINE = "";
    private static ReportService reportService;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeAll() {
        reportService = new ReportServiceImpl();
        storageDao = new StorageDao();
    }

    @After
    public void afterEach() {
        Storage.getFruitStorage().clear();
    }

    @Test
    public void report_NullValueInStorage() {
        storageDao.add(null, null);
        String expected = "null,null" + System.lineSeparator();
        String actual = reportService.createReport(storageDao);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void report_EmptyStorage_ok() {
        String actual = reportService.createReport(storageDao);
        Assert.assertEquals(EMPTY_LINE, actual);
    }

    @Test
    public void report_getCorrectReportFromLines_ok() {
        storageDao.add("banana", 20);
        storageDao.add("apple", 30);
        storageDao.add("milk", 100);
        String expected =
                "banana,20" + System.lineSeparator()
                        + "apple,30" + System.lineSeparator()
                        + "milk,100" + System.lineSeparator();
        String actual = reportService.createReport(storageDao);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void report_getCorrectReportFromOneLine_ok() {
        storageDao.add("banana", 20);
        String expected = "banana,20" + System.lineSeparator();
        String actual = reportService.createReport(storageDao);
        Assert.assertEquals(expected, actual);
    }
}
