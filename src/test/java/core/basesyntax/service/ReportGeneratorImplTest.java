package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static final String HEADER = "fruit,quantity";
    private static ReportGenerator reportGenerator;

    @BeforeClass
    public static void beforeClass() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    public void makeReport_getHeader_ok() {
        String expected = HEADER + System.lineSeparator();
        String actual = reportGenerator.makeReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void makeReport_generateActualReport_ok() {
        String expected = HEADER + System.lineSeparator()
                + "apple,200" + System.lineSeparator();
        Storage.storage.put("apple", 200);
        String actual = reportGenerator.makeReport();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
