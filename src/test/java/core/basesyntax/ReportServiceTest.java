package core.basesyntax;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.ReportServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;
    private static Map<String, Integer> storage;

    @BeforeClass
    public static void beforeAll() {
        storage = new HashMap<>();
        reportService = new ReportServiceImpl(new FruitDaoImpl(storage));
    }

    @Test
    public void makeReport_ok() {
        storage.putAll(Map.of(
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
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEach() {
        storage.clear();
    }
}
