package core.basesyntax.servise.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.servise.ReportService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_valid_Ok() {
        Storage.items.put("banana", 23);
        Storage.items.put("apple", 81);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,23" + System.lineSeparator()
                + "apple,81";

        String actual = reportService.createReport();

        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.items.clear();
    }
}
