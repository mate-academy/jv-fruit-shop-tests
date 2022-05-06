package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void setUp() {
        reportService = new ReportServiceImpl(new StorageDaoImpl());
    }

    @Test
    public void makeReport_noTransactionsWereMade_Ok() {
        String actual = reportService.createReport();
        String expected = "fruit,quantity" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void makeReport_validTransactions_Ok() {
        Storage.storage.put(new Fruit("lemon"), 11);
        Storage.storage.put(new Fruit("cocos"), 35);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "lemon,11" + System.lineSeparator()
                + "cocos,35" + System.lineSeparator();
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @After
    public void cleanUp() {
        Storage.storage.clear();
    }
}
