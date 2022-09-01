package core.basesyntax.service.impl;

import core.basesyntax.service.ReporterService;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReporterServiceImplTest {
    private static ReporterService reporterService;
    private static Map<String, Integer> data;

    @BeforeClass
    public static void beforeClass() {
        reporterService = new ReporterServiceImpl();
        data = Map.of("apple", 20, "banana", 100);
    }

    @Test
    public void createReport_correctData_ok() {
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
