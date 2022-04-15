package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;

    @BeforeClass
    public static void setUp() {
        reportService = new ReportServiceImpl(new StorageDaoImpl());
    }

    @Test
    public void makeReport_NoTransactionsWereMade_Ok() {
        String actual = reportService.makeReport();
        String expected = "fruit,quantity" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void makeReport_ValidTransactions_Ok() {
        Storage.storage.put(new Fruit("orange"), 235);
        Storage.storage.put(new Fruit("pear"), 30);
        Storage.storage.put(new Fruit("kiwi"), 100);
        StringBuilder expected = new StringBuilder();
        expected.append("fruit,quantity").append(System.lineSeparator())
                .append("pear,30").append(System.lineSeparator())
                .append("orange,235").append(System.lineSeparator())
                .append("kiwi,100").append(System.lineSeparator());
        String actual = reportService.makeReport();
        assertEquals(expected.toString(), actual);
    }

    @After
    public void cleanUp() {
        Storage.storage.clear();
    }
}
