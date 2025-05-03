package core.basesyntax.servise.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.servise.ReportService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String REPORT_EXAMPLE = "fruit,quantity" + System.lineSeparator()
            + "banana,23" + System.lineSeparator()
            + "apple,81";
    private static ReportService reportService;

    @BeforeClass
    public static void init() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_valid_Ok() {
        Storage.items.put("banana", 23);
        Storage.items.put("apple", 81);

        String expected = REPORT_EXAMPLE;
        String actual = reportService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void clearStorage() {
        Storage.items.clear();
    }
}
