package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitDao = new FruitDaoImpl();
        reportService = new ReportServiceImpl(fruitDao);
    }

    @Before
    public void setUp() throws Exception {
        fruitDao.clear();
    }

    @After
    public void tearDown() throws Exception {
        fruitDao.clear();
    }

    @Test
    public void createReport_createReport_Ok() {
        fruitDao.add("banana", 50);
        fruitDao.add("apple", 30);
        String expected = "banana,50" + System.lineSeparator() + "apple,30";
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_createEmptyReport_Ok() {
        String expected = "";
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }
}
