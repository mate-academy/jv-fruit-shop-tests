package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreatorService;
import core.basesyntax.service.impl.CsvReportCreatorServiceImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String NEW_LINE = System.lineSeparator();
    private static ReportCreatorService fruitService;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void before() {
        fruitDao = new FruitDaoImpl();
        fruitService = new CsvReportCreatorServiceImpl(fruitDao);
    }

    @Before
    public void beforeEach() {
        Storage.fruitStorage.clear();
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void correctReport_Ok() {
        Storage.fruitStorage.put(APPLE, 10);
        Storage.fruitStorage.put(BANANA, 20);
        String expected = "fruit,quantity" + NEW_LINE
                + "apple,10" + NEW_LINE
                + "banana,20";
        String actual = fruitService.getReport();
        assertEquals(expected, actual);
    }

    @Test
    public void emptyStorage_Ok() {
        String expected = "fruit,quantity";
        String actual = fruitService.getReport();
        assertEquals(expected, actual);
    }
}
