package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static final String HEADER = "fruit,quantity";
    private ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        Storage.storage.clear();
    }

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void makeReport_emptyStorage_Ok() {
        String expected = HEADER;
        String actual = reportService.makeReport();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void makeReport_correctStorage_Ok() {
        Storage.storage.put(new Fruit("banana"), 36);
        Storage.storage.put(new Fruit("apple"), 24);
        String expected = HEADER + System.lineSeparator() + "banana,"
                + 36 + System.lineSeparator() + "apple," + 24;
        String actual = reportService.makeReport();
        Assert.assertEquals(expected,actual);
    }
}
