package core.basesyntax.service;

import static java.lang.System.lineSeparator;
import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.AfterClass;
import org.junit.Test;

public class CsvReportCreatorImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String KEY_APPLE = "apple";
    private static final String KEY_BANANA = "banana";
    private static final String EXPECTED = HEADER + lineSeparator()
            + KEY_BANANA + ",100" + lineSeparator()
            + KEY_APPLE + ",200";
    private static ReportCreator reportCreator;

    @Test
    public void createCsvReport_Ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.put(KEY_BANANA, 100);
        fruitDao.put(KEY_APPLE, 200);
        reportCreator = new CsvReportCreatorImpl(fruitDao);
        assertEquals("There was an error when forming a report file.",
                EXPECTED, reportCreator.create());
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
