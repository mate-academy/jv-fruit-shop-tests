package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeClass
    public static void setUpBeforAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Before
    public void setUp() {
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
        Storage.fruits.put("pineapple", 40);
    }

    @Test
    public void createReport_reportData_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator()
                + "pineapple,40";
        String actual = reportGenerator.createReport();
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void createReport_reportDataEmpty_notOk() {
        Storage.fruits.clear();
        String expected = "fruit,quantity";
        String actual = reportGenerator.createReport();
        Assert.assertEquals("Missing data for report creation", expected, actual);
    }

    @After
    public void clearStorage() {
        Storage.fruits.clear();
    }
}
