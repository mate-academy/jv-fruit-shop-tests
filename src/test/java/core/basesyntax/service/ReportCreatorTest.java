package core.basesyntax.service;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorTest {
    private static ReportCreatorService reportCreatorService;

    @BeforeClass
    public static void beforeClass() {
        reportCreatorService = new ReportCreatorServiceImpl(new StorageDaoImpl());
    }

    @Test
    public void reportCreatorService_createReport_Ok() {
        storage.add(new Fruit("banana", 100));
        storage.add(new Fruit("orange", 10));
        String expectedBanana = "banana,100";
        String expectedOrange = "orange,10";
        String actualBanana = reportCreatorService.createReport().get(0);
        String actualOrange = reportCreatorService.createReport().get(1);
        assertEquals(expectedBanana, actualBanana);
        assertEquals(expectedOrange, actualOrange);
    }

    @Test
    public void reportCreatorService_createReport_emptyStorage_Ok() {
        assertTrue(reportCreatorService.createReport().isEmpty());
    }

    @After
    public void tearDown() {
        storage.clear();
    }
}
