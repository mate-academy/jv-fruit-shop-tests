package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Store;
import core.basesyntax.service.ReportGeneratorService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorServiceImplTest {
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
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,30" + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "lemon,150" + System.lineSeparator()
                + "tomato,1000";
        String actual = reportGeneratorService.generateReport(Store.FRUIT_STORAGE);
        assertEquals("Expected report: " + System.lineSeparator() + expected
                + "but was:" + System.lineSeparator() + actual,
                expected, actual);
    }

    @Test
    public void generateReport_oneProduct_ok() {
        Store.FRUIT_STORAGE.put("carrot", 5000);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "carrot,5000";
        String actual = reportGeneratorService.generateReport(Store.FRUIT_STORAGE);
        assertEquals("Expected report: " + System.lineSeparator() + expected
                        + "but was:" + System.lineSeparator() + actual,
                expected, actual);
    }

    @Test
    public void generateReport_withoutProduct_notOk() {
        String expected = "fruit,quantity";
        String actual = reportGeneratorService.generateReport(Store.FRUIT_STORAGE);
        assertEquals("Expected report: " + System.lineSeparator() + expected
                        + "but was:" + System.lineSeparator() + actual,
                expected, actual);
    }

    @After
    public void tearDown() {
        Store.FRUIT_STORAGE.clear();
    }
}
