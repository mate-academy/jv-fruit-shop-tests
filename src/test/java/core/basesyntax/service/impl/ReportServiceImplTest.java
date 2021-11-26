package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.bd.Storage;
import core.basesyntax.bd.dao.StorageDao;
import core.basesyntax.bd.dao.impl.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        reportService = new ReportServiceImpl(storageDao);
    }

    @Before
    public void setUp() {
        Storage.fruitStorage.clear();
        storageDao.add(new Fruit("apple"), 30);
        storageDao.add(new Fruit("banana"), 25);
    }

    @Test
    public void createReport_validData_Ok() {
        String expected = "fruit,quantity\nbanana,25\napple,30\n";
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_emptyStorage_Ok() {
        Storage.fruitStorage.clear();
        String expected = "fruit,quantity\n";
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }
}
