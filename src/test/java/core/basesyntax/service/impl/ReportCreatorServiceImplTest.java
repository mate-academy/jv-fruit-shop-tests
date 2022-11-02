package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.service.ReportCreatorService;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private static Map<String, Integer> storage;
    private ReportCreatorService reportCreatorService;
    private StorageDao storageDao;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        storage = new HashMap<>();
        reportCreatorService = new ReportCreatorServiceImpl(storageDao);
    }

    @Test
    public void report_Created_Ok() {
        storageDao.getStorage().put("banana", 50);
        storageDao.getStorage().put("apple", 10);
        String expected = "fruit,quantity" + System.lineSeparator() + "banana,50"
                + System.lineSeparator() + "apple,10";
        String actual = reportCreatorService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void report_Created_notOk() {
        storageDao.getStorage().put("banana", 70);
        storageDao.getStorage().put("apple", 50);
        String expected = "fruit,quantity" + System.lineSeparator() + "banana,50"
                + System.lineSeparator() + "apple,10";
        String actual = reportCreatorService.createReport();
        Assert.assertNotEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        storage.clear();
    }
}
