package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void reportServiceIsValid_Ok() {
        Storage.getStorage().put(new Fruit("banana"), 44);
        String expected = "fruit,quantity"
                + System.lineSeparator() + "banana,44" + System.lineSeparator();
        String actual = reportService.generateReport();
        assertEquals(expected,actual);
    }

    @AfterClass
    public static void cleanUp() {
        Storage.getStorage().clear();
    }
}
