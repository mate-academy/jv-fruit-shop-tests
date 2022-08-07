package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreatorService;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private static ReportCreatorService reportCreatorService;
    private static FruitDao fruitDao;
    private static Storage storage;
    private static String expected;

    @BeforeClass
    public static void beforeClass() {
        storage = new Storage();
        fruitDao = new FruitDaoImpl(storage);
        reportCreatorService = new ReportCreatorServiceImpl(fruitDao);
    }

    @Test
    public void createStringReport_Ok() {
        storage.getFruitStorage().put("banana", 40);
        storage.getFruitStorage().put("apple", 40);
        expected = "fruit,quantity\n"
                + "banana,40\n"
                + "apple,40";
        String actual = reportCreatorService.create();
        assertEquals(expected, actual);
    }

    @Test
    public void createStringReportEmpty_Ok() {

        expected = "fruit,quantity";
        String actual = reportCreatorService.create();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void createStringReport_NotOk() {
        reportCreatorService = new ReportCreatorServiceImpl(null);
        reportCreatorService.create();
    }
}
