package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.DailyReportMaker;
import core.basesyntax.service.impl.DailyReportMakerImpl;
import org.junit.Before;
import org.junit.Test;

public class DailyReportMakerImplTest {
    private DailyReportMaker reportMaker;
    private String expected;

    @Before
    public void setUp() {
        StorageDao dao = new StorageDaoImpl();
        dao.addToStorage(new Fruit("apple", 40));
        dao.addToStorage(new Fruit("orange", 60));
        dao.addToStorage(new Fruit("banana", 1));
        reportMaker = new DailyReportMakerImpl(dao);
        expected = "fruit,quantity"
                + System.lineSeparator()
                + "apple,40"
                + System.lineSeparator()
                + "orange,60"
                + System.lineSeparator()
                + "banana,1";
    }

    @Test
    public void makeReport_Ok() {
        String actual = reportMaker.makeReport();
        assertEquals(expected, actual);
    }
}
