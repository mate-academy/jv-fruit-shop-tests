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
    private static Map<String, Integer> fruitsMap;

    @BeforeClass
    public static void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        fruitsMap = new HashMap<>();
    }

    @After
    public void tearDown() {
        fruitsMap.clear();
    }

    @Test
    public void generateReport_validFormat_ok() {
        fruitsMap.put("plum", 25);
        String expected = String.format("%s,%s%s%s,%d",
                "fruit", "quantity", System.lineSeparator(), "plum", 25);
        String actual = reportGenerator.generateReport(fruitsMap);
        assertEquals(expected, actual);
    }

    @Test
    public void generateReport_emptyFruitMap_ok() {
        String expected = "fruit,quantity";
        String actual = reportGenerator.generateReport(fruitsMap);
        assertEquals(expected, actual);
    }
}
