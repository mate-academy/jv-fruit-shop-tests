package core.basesyntax.sevrice.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.sevrice.ReportCreator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static ReportCreator reportCreator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        FruitDao fruitDao = new FruitDaoImpl();
        reportCreator = new ReportCreatorImpl(fruitDao);
    }

    @Before
    public void setUp() {
        Storage.fruits.put(new Fruit("apple"), 20);
        Storage.fruits.put(new Fruit("pineapple"), 25);
    }

    @Test
    public void createReport_Ok() {
        String actual = reportCreator.createReport();
        StringBuilder expected = new StringBuilder("fruit,quantity");
        expected.append(System.lineSeparator())
                .append("apple,20")
                .append(System.lineSeparator())
                .append("pineapple,25");
        Assert.assertEquals(expected.toString(), actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
