package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.getStorage().put(new Fruit("banana"), 20);
        Storage.getStorage().put(new Fruit("apple"), 20);
    }

    @Test
    public void reportService_createValidReport_Ok() {
        String expected = "banana,20" + System.lineSeparator()
                + "apple,20" + System.lineSeparator();
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }
}
