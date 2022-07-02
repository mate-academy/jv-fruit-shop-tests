package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportCreatorImpl;
import org.junit.After;
import org.junit.Test;

public class ReportCreatorTest {
    private static final FruitDao fruitDao = new FruitDaoImpl();
    private static final ReportCreator reportCreator = new ReportCreatorImpl(fruitDao);
    private static final String SEPARATOR = System.lineSeparator();

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
        String expected = "fruit,quantity" + SEPARATOR
                + "banana,10" + SEPARATOR
                + "strawberry,10";
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }

    @After
    public void clearStorage() {
        Storage.fruits.clear();
    }
}
