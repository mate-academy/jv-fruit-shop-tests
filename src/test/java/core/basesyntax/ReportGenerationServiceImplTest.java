package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.service.ReportGenerationService;
import core.basesyntax.service.impl.ReportGenerationServiceImpl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGenerationServiceImplTest {
    private static ReportGenerationService reportGenerationService;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        reportGenerationService = new ReportGenerationServiceImpl(storageDao);
    }

    @Test
    public void getReport_goodReport_ok() {
        storageDao.putNewValues("banana", 25);
        storageDao.putNewValues("apple", 100);
        storageDao.putNewValues("peach", 50);
        String expected = "fruit,quantity\n" 
                + "banana,25\n"
                + "apple,100\n"
                + "peach,50";
        String actual = reportGenerationService.getReport();
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_emptyStorage_ok() {
        String expected = "fruit,quantity";
        String actual = reportGenerationService.getReport();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        storageDao.getData().clear();
    }
}
