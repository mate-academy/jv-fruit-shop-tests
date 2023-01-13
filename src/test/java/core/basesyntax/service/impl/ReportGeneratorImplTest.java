package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportGenerator;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;
    private final Map<String, Integer> testMap = new HashMap<>();
    private final String report = "fruit,amount" + System.lineSeparator()
            + "pizza,99" + System.lineSeparator()
            + "sausage,13" + System.lineSeparator()
            + "cheese,17";

    @Before
    public void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        testMap.put("sausage",13);
        testMap.put("cheese",17);
        testMap.put("pizza",99);
    }

    @Test
    public void makeReport_ok() {
        String actual = reportGenerator.makeReport(testMap);
        assertEquals("Report data is not correct",report, actual);
    }
}
