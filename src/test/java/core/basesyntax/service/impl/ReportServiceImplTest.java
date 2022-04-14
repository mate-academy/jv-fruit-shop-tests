package core.basesyntax.service.impl;

import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String INFO_LINE = "fruit,quantity";
    private static ReportService reportService;

    @BeforeClass
    public static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_Ok() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 20);
        storage.put("apple", 15);
        String expected = INFO_LINE + System.lineSeparator() + "banana,20"
                + System.lineSeparator() + "apple,15" + System.lineSeparator();
        String actual = reportService.createReport(storage);
        Assert.assertEquals("Report wasn't created correctly!", expected, actual);
    }
}
