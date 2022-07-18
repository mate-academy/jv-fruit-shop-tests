package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static final String HEADER = "fruit,quantity";

    @BeforeClass
    public static void init() {
        FruitDao fruitDao = new FruitDaoImpl();
        reportService = new ReportServiceImpl(fruitDao);
    }

    @Test
    public void makeReport_CorrectFormatOfReport_Ok() {
        Storage.fruits.add(new Fruit("peach", 57));
        Storage.fruits.add(new Fruit("kiwi", 34));
        String expectedReport =
                HEADER + System.lineSeparator()
                + "peach,57" + System.lineSeparator()
                + "kiwi,34" + System.lineSeparator();
        String actualReport = reportService.makeReport();
        Assert.assertEquals(expectedReport, actualReport);
    }

    @Test
    public void makeReport_EmptyStorage_Ok() {
        String expectedReport = HEADER + System.lineSeparator();
        String actualReport = reportService.makeReport();
        Assert.assertEquals("Test failed! Expected report, which contains: {"
                + expectedReport + "} but was: {" + actualReport + "}",
                expectedReport, actualReport);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
