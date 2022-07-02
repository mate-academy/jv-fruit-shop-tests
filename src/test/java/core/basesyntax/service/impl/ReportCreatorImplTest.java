package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.After;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static final FruitDao fruitDao = new FruitDaoImpl();
    private static final ReportCreator reportCreator = new ReportCreatorImpl(fruitDao);

    @Test
    public void noTransactions_ok() {
        String expected = "fruit,quantity";
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void reportWithTransactions_ok() {
        fruitDao.update("banana", 10);
        fruitDao.update("strawberry", 10);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator()
                + "strawberry,10";
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }

    @After
    public void clearStorage() {
        Storage.fruits.clear();
    }
}
