package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportServiceImpl reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.storage.clear();
    }

    @Test
    public void correctReport_Ok() {
        Storage.storage.put(new Fruit("banana"), 152);
        Storage.storage.put(new Fruit("apple"), 90);
        String actual = reportService.createReport();
        String expected = "fruit,quantity"
                + System.lineSeparator() + "banana,152"
                + System.lineSeparator() + "apple,90";
        assertEquals(expected, actual);
    }

    @Test
    public void emptyStorage_Ok() {
        String expected = "fruit,quantity";
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }
}

