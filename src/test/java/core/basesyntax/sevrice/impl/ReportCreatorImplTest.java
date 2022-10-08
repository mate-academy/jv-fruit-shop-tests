package core.basesyntax.sevrice.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.sevrice.ReportCreator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static final Fruit APPLE = new Fruit("apple");
    private static final Fruit PINEAPPLE = new Fruit("pineapple");
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final ReportCreator reportCreator = new ReportCreatorImpl(fruitDao);

    @Before
    public void setUp() {
        Storage.fruits.put(APPLE, 20);
        Storage.fruits.put(PINEAPPLE, 25);
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
}
