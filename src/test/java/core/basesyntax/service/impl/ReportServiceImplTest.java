package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void setUp() {
        reportService = new ReportServiceImpl();
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void convertStorage_toString_ok() {
        Storage.storage.put(new Fruit("banana"), 10);
        Storage.storage.put(new Fruit("apple"), 20);
        Storage.storage.put(new Fruit("cherry"), 30);
        String actual = reportService.getReport();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator()
                + "apple,20" + System.lineSeparator()
                + "cherry,30" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void convertStorage_emptyToString_ok() {
        String actual = reportService.getReport();
        String expected = "fruit,quantity" + System.lineSeparator();
        assertEquals("expected another String",
                expected,
                actual);
    }

    @AfterClass
    public static void clean() {
        Storage.storage.clear();
    }
}
