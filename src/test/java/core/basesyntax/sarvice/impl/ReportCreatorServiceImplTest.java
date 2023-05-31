package core.basesyntax.sarvice.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.CreatorReportService;
import core.basesyntax.service.impl.CreatorReportServiceImpl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private static final String TITLE = "fruit,quantity";
    private static CreatorReportService reportCreateService;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        reportCreateService = new CreatorReportServiceImpl(storageDao);
    }

    @Test
    public void createReport_ok() {
        storageDao.update("banana", 20);
        storageDao.update("apple", 15);
        String expected = TITLE + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,15";
        String actual = reportCreateService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_withoutOperation_ok() {
        String actual = reportCreateService.createReport();
        assertEquals(TITLE, actual);
    }

    @After
    public void tearDown() {
        Storage.storageMap.clear();
    }
}
