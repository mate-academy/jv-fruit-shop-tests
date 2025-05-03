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
    private static Map<Fruit, Integer> map;
    private static StringBuilder report;
    private static ReportConstructor reportConstructor;

    @BeforeClass
    public static void setUp() {
        map = new HashMap<>();
        map.put(new Fruit("banana"), 152);
        map.put(new Fruit("apple"), 90);
        reportConstructor = new ReportConstructorImpl();
        report = new StringBuilder();
        report.append("fruit, quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90")
                .append(System.lineSeparator());
    }

    @Test
    public void generateReport_validData_ok() {
        String actual = reportConstructor.createReport(map);
        String expected = report.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generateReport_emptyStorage_ok() {
        reportConstructor.createReport(Collections.emptyMap());
    }
}
