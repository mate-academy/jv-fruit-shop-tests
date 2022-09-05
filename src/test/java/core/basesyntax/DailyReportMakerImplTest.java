package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.DailyReportMaker;
import core.basesyntax.service.impl.DailyReportMakerImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DailyReportMakerImplTest {
    private StorageDao dao;

    @Before
    public void setUp() {
        dao = new StorageDaoImpl();
    }

    @Test
    public void makeReport_Ok() {
        dao.addToStorage(new Fruit("apple", 40));
        dao.addToStorage(new Fruit("orange", 60));
        dao.addToStorage(new Fruit("banana", 1));
        DailyReportMaker reportMaker = new DailyReportMakerImpl(dao);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "apple,40"
                + System.lineSeparator()
                + "orange,60"
                + System.lineSeparator()
                + "banana,1";
        String actual = reportMaker.makeReport();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
