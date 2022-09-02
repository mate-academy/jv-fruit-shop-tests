package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void reportService_Ok() {
        Storage.getStorage().put(new Fruit("banana"), 44);
        String expected = "fruit,quantity"
                + System.lineSeparator() + "banana,44" + System.lineSeparator();
        assertEquals(expected,reportService.generateReport());
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.getStorage().clear();
    }
}
