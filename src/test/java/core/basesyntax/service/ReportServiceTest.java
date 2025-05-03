package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.impl.ReportServiceImpl;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static FruitDao dao;
    private static ReportService reportService;

    @BeforeClass
    public static void beforeAll() {
        dao = new FruitDaoImpl();
        reportService = new ReportServiceImpl(new FruitDaoImpl());
    }

    @Test
    public void makeReport_ok() {
        Storage.fruitsMap.putAll(Map.of(
                "apple", 65,
                "banana", 62,
                "orange", 6,
                "grapefruit", 1085));
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,65" + System.lineSeparator()
                + "banana,62" + System.lineSeparator()
                + "grapefruit,1085" + System.lineSeparator()
                + "orange,6";
        String actual = reportService.makeReport();
        Assert.assertEquals("expected: " + expected + ", actual: " + actual, expected, actual);
    }

    @After
    public void afterEach() {
        Storage.fruitsMap.clear();
    }
}
