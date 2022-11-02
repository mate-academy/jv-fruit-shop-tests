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
    private static final String TEST_NAME_1 = "banana";
    private static final int TEST_VALUE_1 = 20;
    private static final String TEST_RESULT_1 = "banana,20";
    private static final String TEST_NAME_2 = "apple";
    private static final int TEST_VALUE_2 = 30;
    private static final String TEST_RESULT_2 = "apple,30";
    private static final String TEST_NAME_3 = "milk";
    private static final int TEST_VALUE_3 = 100;
    private static final String TEST_RESULT_3 = "milk,100";
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
        storageDao.add(TEST_NAME_1, TEST_VALUE_1);
        storageDao.add(TEST_NAME_2, TEST_VALUE_2);
        storageDao.add(TEST_NAME_3, TEST_VALUE_3);
        String expected =
                TEST_RESULT_1 + System.lineSeparator()
                        + TEST_RESULT_2 + System.lineSeparator()
                        + TEST_RESULT_3 + System.lineSeparator();
        String actual = reportService.createReport(storageDao);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void report_getCorrectReportFromOneLine_ok() {
        storageDao.add(TEST_NAME_1, TEST_VALUE_1);
        String expected = TEST_RESULT_1 + System.lineSeparator();
        String actual = reportService.createReport(storageDao);
        Assert.assertEquals(expected, actual);
    }
}
