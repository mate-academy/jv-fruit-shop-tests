package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        reportGenerator = new ReportGeneratorImpl(fruitDao);
    }

    @Before
    public void setUp() {
        Storage.fruits.put(new Fruit("apple"), 35);
        Storage.fruits.put(new Fruit("mango"), 25);
    }

    @Test
    public void createReport_Ok() {
        String actual = reportGenerator.generateReport();
        StringBuilder expected = new StringBuilder("fruit,quantity");
        expected.append(System.lineSeparator())
                .append("apple,35")
                .append(System.lineSeparator())
                .append("mango,25");
        Assert.assertEquals(expected.toString(), actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
