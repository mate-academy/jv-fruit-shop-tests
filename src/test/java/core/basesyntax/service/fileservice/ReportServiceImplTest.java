package core.basesyntax.service.fileservice;

import static java.lang.System.lineSeparator;
import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import org.junit.AfterClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String KEY_APPLE = "apple";
    private static final String KEY_BANANA = "banana";
    private static final String SEPARATOR = ",";
    private static final String EXPECTED = HEADER + lineSeparator()
            + KEY_BANANA + SEPARATOR + "100" + lineSeparator()
            + KEY_APPLE + SEPARATOR + "200";
    private static ReportService reportService;

    @Test
    public void getReportIs_Ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.put(KEY_APPLE,200);
        fruitDao.put(KEY_BANANA,100);
        reportService = new ReportServiceImpl(fruitDao);
        assertEquals("Can't create report file", EXPECTED,reportService.getReport());
    }

    @AfterClass
    public static void afterClass() {
        FruitStorage.storage.clear();
    }
}
