package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;
    private static final Map<Fruit, Integer> fruitStorage = new HashMap<>();
    private static final String EXPECTED =
            "fruit,quantity" + System.lineSeparator()
            + "banana,50" + System.lineSeparator()
            + "apple,70";

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
        fruitStorage.put(new Fruit("banana"), 50);
        fruitStorage.put(new Fruit("apple"), 70);
    }

    @Test
    public void validReport_Ok() {
        String actual = reportService.createReport(fruitStorage);
        Assert.assertEquals(EXPECTED, actual);
    }

    @Test(expected = NullPointerException.class)
    public void nullReport_notOk() {
        reportService.createReport(null);
    }

    @Test
    public void emptyMapReport_notOk() {
        Map<Fruit, Integer> emptyStorage = new HashMap<>();
        String actual = reportService.createReport(emptyStorage);
        String expected = "fruit,quantity";
        Assert.assertEquals(expected, actual);
    }
}
