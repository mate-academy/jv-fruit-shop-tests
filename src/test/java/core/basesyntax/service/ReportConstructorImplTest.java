package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportConstructorImpl;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportConstructorImplTest {
    private static Map<Fruit, Integer> testMap;
    private static StringBuilder testReport;
    private static ReportConstructor testReportConstructor;

    @BeforeClass
    public static void setUp() {
        testMap = new HashMap<>();
        testMap.put(new Fruit("banana"), 152);
        testMap.put(new Fruit("apple"), 90);
        testReportConstructor = new ReportConstructorImpl();
        testReport = new StringBuilder();
        testReport.append("fruit, quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90")
                .append(System.lineSeparator());
    }

    @Test
    public void generateReport_validData_ok() {
        String actual = testReportConstructor.createReport(testMap);
        String expected = testReport.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generateReport_emptyStorage_ok() {
        testReportConstructor.createReport(Collections.emptyMap());
    }
}
