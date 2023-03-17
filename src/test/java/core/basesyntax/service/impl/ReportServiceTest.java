package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static FruitDao fruitDao;
    private static ReportService reportService;
    private static final String FRUIT1_TEST = "banana";
    private static final int AMOUNT1_TEST = 45;
    private static final String FRUIT2_TEST = "apple";
    private static final int AMOUNT2_TEST = 5;
    private static final String STRING1_REPORT = "fruit,quantity";
    private static final String STRING2_REPORT = FRUIT1_TEST + "," + AMOUNT1_TEST;
    private static final String STRING3_REPORT = FRUIT2_TEST + "," + AMOUNT2_TEST;
    private static final String REPORT_TEST = STRING1_REPORT + System.lineSeparator()
            + STRING2_REPORT + System.lineSeparator()
            + STRING3_REPORT + System.lineSeparator();

    @BeforeClass
    public static void setUp() {
        fruitDao = new FruitDaoImpl();
        fruitDao.clear();
        fruitDao.put(FRUIT1_TEST, AMOUNT1_TEST);
        fruitDao.put(FRUIT2_TEST, AMOUNT2_TEST);
        reportService = new ReportService(fruitDao);
    }

    @Test
    public void generateReport_Ok() {
        assertEquals(REPORT_TEST, reportService.generateReport());
    }

    @After
    public void tearDown() {
        fruitDao.clear();
    }
}
