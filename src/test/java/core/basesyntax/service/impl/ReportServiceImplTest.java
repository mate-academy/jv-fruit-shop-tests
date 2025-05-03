package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
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
        Storage.clear();
        Storage.add(new Fruit("apple"), 20);
        Storage.add(new Fruit("banana"), 30);
        Storage.add(new Fruit("orange"), 33);
    }

    @Test
    public void reportServiceImpl_validData_Ok() {
        String actual = reportService.createReport();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,30" + System.lineSeparator()
                + "apple,20" + System.lineSeparator()
                + "orange,33" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void reportServiceImpl_emptyStorage_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        Storage.clear();
        assertEquals(expected, reportService.createReport());
    }
}
