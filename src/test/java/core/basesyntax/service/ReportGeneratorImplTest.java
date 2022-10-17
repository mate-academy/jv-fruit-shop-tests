package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.After;
import org.junit.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

public class ReportGeneratorImplTest {
    private final static Fruit APPLE = new Fruit("apple");
    private final static Fruit BANANA = new Fruit("banana");
    private final static Fruit MANGO = new Fruit("mango");

    private static ReportGenerator reportGenerator;
    private static final String FIRST_LINE = "fruit,quantity";
    private static final String ADDITIONAL_LINE = System.lineSeparator();

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        reportGenerator = new ReportGeneratorImpl(fruitDao);
    }

    @Test
    public void report_emptyStorage_Ok() {
        String expected = FIRST_LINE;
        String actualReport = reportGenerator.generateReport();
        Assert.assertEquals(expected, actualReport);
    }

    @Test
    public void report_storageWithData_Ok() {
        Storage.fruits.put(APPLE, 120);
        Storage.fruits.put(BANANA, 50);
        Storage.fruits.put(MANGO, 90);
        String expected = FIRST_LINE + ADDITIONAL_LINE
                + "apple,120" + ADDITIONAL_LINE
                + "banana,50" + ADDITIONAL_LINE
                + "mango,90";
        String actual = reportGenerator.generateReport();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
