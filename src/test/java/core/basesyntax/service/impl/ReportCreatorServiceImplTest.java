package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreatorService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private static ReportCreatorService reportCreatorService;

    @BeforeClass
    public static void beforeClass() {
        StorageDao storageDao = new StorageDaoImpl();
        reportCreatorService = new ReportCreatorServiceImpl(storageDao);
    }

    @Test
    public void createReport_validData_Ok() {
        Storage.storage.put(new Fruit("apple"), 100);
        Storage.storage.put(new Fruit("orange"), 100);
        String actual = reportCreatorService.createReport();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "orange,100" + System.lineSeparator();
        assertEquals(expected,actual);
    }

    @Test
    public void createReport_emptyStorage_Ok() {
        String actual = reportCreatorService.createReport();
        String expected = "fruit,quantity" + System.lineSeparator();
        assertEquals(expected,actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
