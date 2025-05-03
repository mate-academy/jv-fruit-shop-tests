package core.basesyntax.service;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;
    private static Map<String, Integer> dataMap;
    private String expected;
    private String actual;

    @BeforeClass
    public static void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        dataMap = new HashMap<>();
        dataMap.put("banana",10);
        dataMap.put("avocado",12);
    }

    @Test
    public void checkValidReport_Ok() {
        expected = "fruit,quantity"
                + System.lineSeparator() + "banana,10"
                + System.lineSeparator() + "avocado,12";
        actual = reportGenerator.generateReport(dataMap);
        Assert.assertEquals(expected, actual);
    }
}
