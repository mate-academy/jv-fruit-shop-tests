package core.basesyntax.service.implemantation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportGenerationService;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ReportGenerationServiceImplTest {
    private ReportGenerationService reportGenerator;
    private Map<String, Integer> testMap;

    @Before
    public void setUp() {
        reportGenerator = new ReportGenerationServiceImpl();
        testMap = new HashMap<>();
        testMap.put("banana", 74);
        testMap.put("berry", 214);
    }

    @Test
    public void generateReport_correctData_ok() {
        String actual = reportGenerator.generateReport(testMap);
        String expected = "fruit, quantity" + System.lineSeparator()
                 + "banana,74" + System.lineSeparator()
                 + "berry,214";
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void generateReport_inputMapNull_notOk() {
        reportGenerator.generateReport(null);
    }
}
