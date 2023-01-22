package core.basesyntax.service.impl;

import core.basesyntax.service.ReportGenerator;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static final String HEADER = "fruit,quantity";
    private static ReportGenerator reportGenerator;

    @BeforeClass
    public static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    public void createReport_Ok() {
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("banana", 20);
        fruits.put("apple", 15);
        String expected = HEADER + System.lineSeparator() + "banana,20"
                + System.lineSeparator() + "apple,15" + System.lineSeparator();
        String actual = reportGenerator.generateReport(fruits);
        Assert.assertEquals("Report wasn't created correctly.", expected, actual);
    }
}
