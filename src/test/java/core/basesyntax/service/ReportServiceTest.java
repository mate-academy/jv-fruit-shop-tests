package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;
    private static StorageDao storageDao;

    @BeforeClass
    public static void setUpFirst() {
        storageDao = new StorageDaoImpl();
        reportService = new ReportServiceImpl(storageDao);
    }

    @Test
    public void report_emptyStorage_Ok() {
        String actual = reportService.report();
        String expected = "fruit,quantity";
        assertEquals(expected, actual);
    }

    @Test
    public void report_notEmptyStorage_Ok() {
        storageDao.add(new Fruit("orange"), 5);
        String actual = reportService.report();
        String expected = "fruit,quantity\norange,5";
        assertEquals(expected, actual);
    }

    @After
    public void clear() {
        storageDao.clear();
    }
}
