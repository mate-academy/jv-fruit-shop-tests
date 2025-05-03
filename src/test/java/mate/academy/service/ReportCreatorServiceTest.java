package mate.academy.service;

import java.util.HashMap;
import java.util.Map;
import mate.academy.service.impl.ReportCreatorServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceTest {
    private static ReportCreatorService reportCreatorService;
    private Map<String, Integer> fruits = new HashMap<>();

    @BeforeClass
    public static void beforeClass() {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @After
    public void tearDown() {
        fruits.clear();
    }

    @Test
    public void createReport() {
        fruits.put("banana", 25);
        fruits.put("apple", 25);
        String actual = reportCreatorService.createReport(fruits);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,25" + System.lineSeparator() + "apple,25" + System.lineSeparator();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createEmptyReport() {
        String actual = reportCreatorService.createReport(fruits);
        String expected = "fruit,quantity" + System.lineSeparator();
        Assert.assertEquals(expected, actual);
    }
}
