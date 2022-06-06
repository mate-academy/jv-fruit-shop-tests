package core.basesyntax.services;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static String actual;

    @BeforeClass
    public static void beforeAll() {
        reportService = new ReportServiceImpl();
        actual = "fruit,quantity" + System.lineSeparator()
                + "banana 20" + System.lineSeparator()
                + "orange 20" + System.lineSeparator()
                + "apple 20" + System.lineSeparator();
    }

    @Test
    public void createReport_Ok() {
        Map<String, Integer> testMapOfFruit = new HashMap<>();
        testMapOfFruit.put("banana",20);
        testMapOfFruit.put("apple", 20);
        testMapOfFruit.put("orange", 20);
        Assert.assertEquals(actual, reportService.createReport(testMapOfFruit));
    }
}
