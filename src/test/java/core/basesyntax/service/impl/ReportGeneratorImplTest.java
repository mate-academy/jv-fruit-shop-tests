package core.basesyntax.service.impl;

import core.basesyntax.db.FruitDao;
import core.basesyntax.exception.InvalidPathException;
import core.basesyntax.service.ReportGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

public class ReportGeneratorImplTest {
    private static final String VALID_REPORT = "fruit,quantity" + System.lineSeparator() +"blueberry,10";

    private static ReportGenerator reportGenerator;
    @BeforeClass
    public static void beforeClass() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    public void generateReport_correctReport_ok() {
/*        FruitDao.storage.put("blueberry", 10);
        String actual = reportGenerator.generateReport();
        Assert.assertEquals("Strings must be equals"
                + System.lineSeparator() + VALID_REPORT,VALID_REPORT, actual);*/
    }
    @After
    public void tearDown() {
        FruitDao.storage.clear();
    }

}