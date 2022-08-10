package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private static ReportCreatorService reportCreatorService;

    @BeforeClass
    public static void beforeClass() {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @Test
    public void makeReport_defaultCase_Ok() {
        FruitStorage.fruitsMap.put(Util.banana, 50);
        FruitStorage.fruitsMap.put(Util.apple, 65);
        FruitStorage.fruitsMap.put(Util.lemon, 80);
        String expected = Util.createTextFromLines(Util.REPORT);
        String actual = reportCreatorService.makeReport();
        assertEquals("makeReport should return text report: "
                + expected + " but was: "
                + actual, expected, actual);
    }

    @Test
    public void makeReport_emptyFruitStorage_Ok() {
        String expected = "fruit,quantity";
        String actual = reportCreatorService.makeReport();
        assertEquals("makeReport should return empty report: "
                + expected + " but was: "
                + actual, expected, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruitsMap.clear();
    }
}
