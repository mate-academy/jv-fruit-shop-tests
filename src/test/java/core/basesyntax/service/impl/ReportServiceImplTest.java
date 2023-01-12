package core.basesyntax.service.impl;

import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String VALID_REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,130" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private static ReportService reportService;
    private static Map<String, Integer> fruitMap;

    @BeforeClass
    public static void setUp() {
        reportService = new ReportServiceImpl();
        fruitMap = new HashMap<>();
        fruitMap.put("banana", 130);
        fruitMap.put("apple", 90);
    }

    @Test
    public void getReport_validMap_ok() {
        String actual = reportService.getReport(fruitMap);
        String expected = VALID_REPORT;
        Assert.assertEquals("Method have to return valid report: "
                + System.lineSeparator() + VALID_REPORT, expected, actual);
    }

    @Test
    public void getReport_emptyMap_ok() {
        String actual = reportService.getReport(new HashMap<>());
        String expected = "fruit,quantity" + System.lineSeparator();
        Assert.assertEquals("Method have to return empty report: "
                + System.lineSeparator() + VALID_REPORT, expected, actual);
    }

    @Test
    public void getReport_notValidMap_notOk() {
        String actual = reportService.getReport(fruitMap);
        String expected = VALID_REPORT + " ";
        Assert.assertNotEquals("Method have to return valid report: "
                + System.lineSeparator() + VALID_REPORT, expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void getReport_null_notOk() {
        reportService.getReport(null);
    }
}
