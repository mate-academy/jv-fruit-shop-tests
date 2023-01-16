package core.basesyntax.service.impl;

import core.basesyntax.db.FruitDao;
import core.basesyntax.service.ReportGenerator;
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
    public void generateReport_emptyStorage_ok() {
        FruitDao.storage.clear();
        String expected = HEADER + System.lineSeparator();
        String actual = reportGenerator.generateReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generateReport_correctReport_ok() {
        FruitDao.storage.put("blueberry", 10);
        String expected = HEADER
                + System.lineSeparator()
                + "blueberry,10"
                + System.lineSeparator();
        String actual = reportGenerator.generateReport();
        Assert.assertEquals("Strings must be the same", expected, actual);
    }

    @After
    public void tearDown() {
        FruitDao.storage.clear();
    }
}
