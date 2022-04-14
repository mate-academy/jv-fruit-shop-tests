package core.basesyntax.services;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static Map<Fruit, Integer> mapQuantityFruit;
    private static final String TITLE_REPORT = "fruit,quantity";
    private static ReportService reportService;

    @Before
    public void setUp() {
        mapQuantityFruit = new HashMap<>();
        mapQuantityFruit.put(new Fruit("banana"), 152);
        mapQuantityFruit.put(new Fruit("apple"), 90);
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_Ok() {
        String actual = reportService.createReport(mapQuantityFruit,TITLE_REPORT);
        String expected = "fruit,quantity" + System.lineSeparator() + "banana,152"
                + System.lineSeparator() + "apple,90" + System.lineSeparator();
        assertEquals(actual,expected);
    }
}
