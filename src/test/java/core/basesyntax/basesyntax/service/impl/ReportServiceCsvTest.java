package core.basesyntax.basesyntax.service.impl;

import core.basesyntax.basesyntax.db.Storage;
import core.basesyntax.basesyntax.model.Fruit;
import core.basesyntax.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceCsvTest {
    private static ReportService reportService;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static Map<Fruit, Integer> fruits;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportService = new ReportServiceCsv();
        fruits = new HashMap<>();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void createReport_isOk() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        Fruit apple = new Fruit(APPLE);
        Fruit banana = new Fruit(BANANA);
        fruits.put(apple, 90);
        fruits.put(banana, 152);
        String actual = reportService.createReport(fruits);
        Assert.assertEquals(expected, actual);
    }
}
