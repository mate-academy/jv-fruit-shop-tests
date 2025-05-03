package basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGenerationImpl;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGenerationImplTest {
    private static Map<Fruit, Integer> testMap;
    private static StringBuilder testReport;
    private static ReportGenerator testReportGenerator;

    @BeforeClass
    public static void setUp() {
        testMap = new HashMap<>();
        testMap.put(new Fruit("banana"), 152);
        testMap.put(new Fruit("apple"), 90);
        testReportGenerator = new ReportGenerationImpl();
        testReport = new StringBuilder();
        testReport.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90")
                .append(System.lineSeparator());
    }

    @Test
    public void generateReport_validData_ok() {
        String actual = testReportGenerator.generateReport(testMap);
        String expected = testReport.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generateReport_emptyStorage_ok() {
        testReportGenerator.generateReport(Collections.emptyMap());
    }

    @Test(expected = RuntimeException.class)
    public void reportNullData_notOk() {
        testReportGenerator.generateReport(null);
    }
}
