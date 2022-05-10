package service;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.impl.ReportServiceImpl;

public class ReportServiceTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void reportService_OK() {
        Map<String, Integer> storeFruits = new HashMap<>();
        String expected = "fruit,quantity"
                + System.lineSeparator() + "banana,152"
                + System.lineSeparator() + "apple,90";
        storeFruits.put("banana", 152);
        storeFruits.put("apple", 90);
        String actual = reportService.generateReport(storeFruits);
        Assert.assertEquals(expected, actual);
    }
}
