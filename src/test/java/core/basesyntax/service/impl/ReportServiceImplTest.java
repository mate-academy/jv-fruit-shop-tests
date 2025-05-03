package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String DEFAULT_REPORT = "fruit,quantity" + System.lineSeparator()
            + "carrot,25" + System.lineSeparator();
    private static final String DEFAULT_EMPTY_REPORT = "fruit,quantity" + System.lineSeparator();
    private static final Fruit TEST_FRUIT = new Fruit("carrot", 25);
    private static ReportService reportService;

    @BeforeClass
    public static void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void reportService_creatingReport_ok() {
        Storage.fruits.add(TEST_FRUIT);
        String expected = DEFAULT_REPORT;
        String actual = reportService.report();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void reportService_emptyStorageReport_ok() {
        String actual = reportService.report();
        String expected = DEFAULT_EMPTY_REPORT;
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
