package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static StringBuilder expectedReport = new StringBuilder();
    private ReportService reportServiceTest = new ReportServiceImpl();

    @BeforeClass
    public static void beforeClass() {
        expectedReport.append("fruit ,quantity ");
        expectedReport.append(System.lineSeparator());
        expectedReport.append("banana,100");
        expectedReport.append(System.lineSeparator());
        expectedReport.append("apple,100");
        expectedReport.append(System.lineSeparator());
    }

    @Test
    public void testReport_ok() {
        Storage.storage.put(new Fruit("banana"), 20);
        Storage.storage.put(new Fruit("apple"), 100);
        Storage.storage.put(new Fruit("banana"), 13);
        Storage.storage.put(new Fruit("banana"), 100);
        String actual = reportServiceTest.getReport();
        assertEquals(expectedReport.toString(), actual);
    }

    @After
    public void clearStorage() {
        Storage.storage.clear();
    }
}

