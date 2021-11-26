package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static Map<String, Integer> reportTestMap;

    @BeforeClass
    public static void setUp() {
        reportService = new ReportServiceImpl();
        reportTestMap = Storage.storage;
    }

    @Test
    public void formReport_formValidReport_ok() {
        reportTestMap.put("banana",152);
        String actual = reportService.formReport(reportTestMap.entrySet());
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,152";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void formReport_formEmptyReport_ok() {
        Assert.assertEquals("fruit,quantity",
                reportService.formReport(reportTestMap.entrySet()));
    }

    @After
    public void afterEachTest() {
        reportTestMap.clear();
    }
}
