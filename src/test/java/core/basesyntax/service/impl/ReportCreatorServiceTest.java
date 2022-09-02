package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportCreatorServiceTest {
    private static final String SEPARATOR = System.lineSeparator();
    private ReportCreatorService reportCreatorService;

    @Before
    public void setUp() {
        reportCreatorService = new ReportCreatorService();
    }

    @Test
    public void report_Ok() {
        String expected = "fruit,quantity"
                + SEPARATOR
                + "banana,17"
                + SEPARATOR
                + "apple,12";
        Storage.storage.put(new Fruit("banana"), 17);
        Storage.storage.put(new Fruit("apple"), 12);
        String actual = reportCreatorService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void report_NotOk() {
        String expected = "fruit,quantity"
                + SEPARATOR
                + "banana,17"
                + SEPARATOR
                + "apple,78";
        String actual = reportCreatorService.createReport();
        Assert.assertNotEquals(expected, actual);
    }
}
