package core.basesyntax.service.fileservice;

import static java.lang.System.lineSeparator;
import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import org.junit.After;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String KEY_APPLE = "apple";
    private static final String KEY_BANANA = "banana";
    private static final String SEPARATOR = ",";
    private static ReportService reportService;

    @Test
    public void getReport_EmptyStorage_IsOk() {
        final String Expected = HEADER;
        FruitDao fruitDao = new FruitDaoImpl();
        reportService = new ReportServiceImpl(fruitDao);
        assertEquals(Expected,reportService.getReport());
    }

    @Test
    public void getReport_IsOk() {
        final String Expected = HEADER + lineSeparator()
                + KEY_BANANA + SEPARATOR + "100" + lineSeparator()
                + KEY_APPLE + SEPARATOR + "200";
        FruitDao fruitDao = new FruitDaoImpl();
        FruitStorage.storage.put(KEY_APPLE,200);
        FruitStorage.storage.put(KEY_BANANA,100);
        reportService = new ReportServiceImpl(fruitDao);
        assertEquals("Can't create report file", Expected,reportService.getReport());
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
