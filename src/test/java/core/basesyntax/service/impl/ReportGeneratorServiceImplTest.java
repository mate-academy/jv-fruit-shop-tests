package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Store;
import core.basesyntax.service.ReportGeneratorService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorServiceImplTest {
    private static final String SEPARATOR = System.lineSeparator();
    private static ReportGeneratorService reportGeneratorService;

    @BeforeClass
    public static void beforeClass() {
        reportGeneratorService = new ReportGeneratorServiceImpl();
    }

    @Test
    public void generateReport_fourTypeOfProduct_ok() {
        Store.FRUIT_STORAGE.put("banana", 30);
        Store.FRUIT_STORAGE.put("apple", 100);
        Store.FRUIT_STORAGE.put("lemon", 150);
        Store.FRUIT_STORAGE.put("tomato", 1000);
        String expected = "fruit,quantity" + SEPARATOR
                + "banana,30" + SEPARATOR
                + "apple,100" + SEPARATOR
                + "lemon,150" + SEPARATOR
                + "tomato,1000";
        String actual = reportGeneratorService.generateReport(Store.FRUIT_STORAGE);
        assertEquals("Expected report: " + SEPARATOR + expected
                + "but was:" + SEPARATOR + actual,
                expected, actual);
    }

    @Test
    public void generateReport_oneProduct_ok() {
        Store.FRUIT_STORAGE.put("carrot", 5000);
        String expected = "fruit,quantity" + SEPARATOR
                + "carrot,5000";
        String actual = reportGeneratorService.generateReport(Store.FRUIT_STORAGE);
        assertEquals("Expected report: " + SEPARATOR + expected
                        + "but was:" + SEPARATOR + actual,
                expected, actual);
    }

    @Test
    public void generateReport_withoutProduct_notOk() {
        String expected = "fruit,quantity";
        String actual = reportGeneratorService.generateReport(Store.FRUIT_STORAGE);
        assertEquals("Expected report: " + SEPARATOR + expected
                        + "but was:" + SEPARATOR + actual,
                expected, actual);
    }

    @After
    public void tearDown() {
        Store.FRUIT_STORAGE.clear();
    }
}
