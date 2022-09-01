package core.basesyntax.service.impl;

import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
    public void validData_ok() {
        StringBuilder stringBuilder = new StringBuilder();
        String expectedString = stringBuilder.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("apple,10")
                .toString();
        Map<String, Integer> data = new HashMap<>();
        data.put("apple", 10);
        Set<Map.Entry<String, Integer>> entries = data.entrySet();
        String actualString = reportService.createReport(entries);
        Assert.assertEquals("Expected equals with valid string", expectedString, actualString);
    }

    @Test (expected = NullPointerException.class)
    public void nullValue_notOk() {
        reportService.createReport(null);
    }
}
