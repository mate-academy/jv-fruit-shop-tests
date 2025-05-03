package core.service.impl;

import static org.junit.Assert.assertEquals;

import core.service.ReportService;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void reportService_createReport_Ok() {
        Map<String, Integer> data = new HashMap<>();
        data.put("banana", 100);
        data.put("apple", 50);
        String expected = "banana,100" + System.lineSeparator() + "apple,50";
        String actual = reportService.create(data);
        assertEquals(expected, actual);
    }

    @Test
    public void reportService_reportWithEmptyData_Ok() {
        String actual = reportService.create(new HashMap<>());
        String expected = "";
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void reportService_reportWithNullData_NotOk() {
        reportService.create(null);
    }
}
