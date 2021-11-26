package core.basesyntax.services.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.services.ReportService;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static Map<String, Integer> reportTestMap;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
        reportTestMap = Storage.storage;
    }

    @Test
    public void reportService_ValidReport_ok() {
        reportTestMap.put("banana",152);
        String actual = reportService.formReport();
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,152";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void reportService_reportEmpty() {
        Assert.assertEquals("fruit,quantity", reportService.formReport());
    }

    @After
    public void afterEachTest() {
        reportTestMap.clear();
    }
}
