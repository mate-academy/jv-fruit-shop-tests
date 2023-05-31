package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;
    private String report;

    @BeforeClass
    public static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Before
    public void setUp() {
        Storage.storage.put(new Fruit("peach"), 78);
        Storage.storage.put(new Fruit("apple"), 90);
        report = "fruit,quantity" + System.lineSeparator()
                + "apple,90" + System.lineSeparator()
                + "peach,78" + System.lineSeparator();
    }

    @Test
    public void service_reportGeneratorGenerate_ok() {
        String expected = report;
        String actual = reportGenerator.generateReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void service_reportReportGeneratorEmptyString_notOk() {
        Storage.storage.clear();
        String expected = "";
        String actual = reportGenerator.generateReport();
        Assert.assertNotEquals(expected, actual);
    }

    @After
    public void clear() {
        Storage.storage.clear();
    }
}
