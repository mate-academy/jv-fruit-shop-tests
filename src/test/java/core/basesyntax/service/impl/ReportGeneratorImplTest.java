package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportGenerator;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;
    private static Map<String, Integer> fruits;

    @BeforeClass
    public static void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        fruits = new HashMap<>();
    }

    @After
    public void tearDown() {
        fruits.clear();
    }

    @Test
    public void generateReport_validFormat_ok() {
        fruits.put("plum", 25);
        String expected = String.format("%s,%s%s%s,%d",
                "fruit", "quantity", System.lineSeparator(), "plum", 25);
        String actual = reportGenerator.generateReport(fruits);
        assertEquals(expected, actual);
    }
}
