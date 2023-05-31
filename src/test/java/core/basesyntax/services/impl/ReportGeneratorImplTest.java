package core.basesyntax.services.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.services.ReportGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;
    private static final String FIRST_LINE = "fruit,quantity";
    private static final String ADDITIONAL_REPORT_LINE = System.lineSeparator();

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        reportGenerator = new ReportGeneratorImpl(fruitDao);
    }

    @Test
    public void report_emptyStorage_Ok() {
        String expected = FIRST_LINE;
        String actualReport = reportGenerator.generate();
        Assert.assertEquals(expected, actualReport);
    }

    @Test
    public void report_storageWithData_Ok() {
        Storage.getFruitsStorage().put("cherry", 380);
        Storage.getFruitsStorage().put("apple", 30);
        Storage.getFruitsStorage().put("melon", 200);
        String expected = FIRST_LINE + ADDITIONAL_REPORT_LINE
                + "cherry,380" + ADDITIONAL_REPORT_LINE
                + "apple,30" + ADDITIONAL_REPORT_LINE
                + "melon,200";
        String actual = reportGenerator.generate();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.getFruitsStorage().clear();
    }
}
