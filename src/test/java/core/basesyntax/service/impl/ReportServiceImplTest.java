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
    public void makeReport_NoTransactionsWereMade_Ok() {
        String actual = reportService.createReport();
        String expected = "fruit,quantity" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void makeReport_ValidTransactions_Ok() {
        Storage.storage.put(new Fruit("lemon"), 11);
        Storage.storage.put(new Fruit("cocos"), 35);

        StringBuilder expected = new StringBuilder();
        expected.append("fruit,quantity").append(System.lineSeparator())
                .append("lemon,11").append(System.lineSeparator())
                .append("cocos,35").append(System.lineSeparator());
        String actual = reportService.createReport();
        assertEquals(expected.toString(), actual);
    }

    @After
    public void cleanUp() {
        Storage.storage.clear();
    }
}
