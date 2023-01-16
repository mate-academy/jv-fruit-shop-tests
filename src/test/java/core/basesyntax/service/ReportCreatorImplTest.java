package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportCreatorImpl;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static Map<Fruit, Integer> storage;
    private static StringBuilder report;
    private static ReportCreator reportCreator;

    @BeforeClass
    public static void setUp() {
        storage = new HashMap<>();
        storage.put(new Fruit("banana"), 152);
        storage.put(new Fruit("apple"), 90);
        reportCreator = new ReportCreatorImpl();
        report = new StringBuilder();
        report.append("fruit, quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90")
                .append(System.lineSeparator());
    }

    @Test
    public void createReport_validData_ok() {
        String actual = reportCreator.createReport(storage);
        String expected = report.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createReport_emptyStorage_ok() {
        String actual = reportCreator.createReport(Collections.emptyMap());
        String expected = "fruit, quantity" + System.lineSeparator();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void createReport_nullData_notOk() {
        reportCreator.createReport(null);
    }
}
