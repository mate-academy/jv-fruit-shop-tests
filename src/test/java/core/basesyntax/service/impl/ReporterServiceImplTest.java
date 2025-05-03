package core.basesyntax.service.impl;

import core.basesyntax.service.ReporterService;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReporterServiceImplTest {
    private static ReporterService reporterService;

    @BeforeClass
    public static void beforeClass() {
        reporterService = new ReporterServiceImpl();
    }

    @Test
    public void createReport_correctData_ok() {
        Map<String, Integer> data = new HashMap<>();
        data.put("banana", 100);
        data.put("apple", 20);
        String actual = reporterService.createReport(data.entrySet());
        StringBuilder stringBuilder = new StringBuilder("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,100")
                .append(System.lineSeparator())
                .append("apple,20");
        String expected = stringBuilder.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void createReport_nullData_notOk() {
        reporterService.createReport(null);
    }
}
