package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGeneratorService;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorServiceImplTest {
    private static final String SEPARATOR = System.lineSeparator();
    private static ReportGeneratorService reportGeneratorService;
    private static final String FIRST_LINE = "fruit,quantity";

    @BeforeClass
    public static void beforeClass() {
        reportGeneratorService = new ReportGeneratorServiceImpl();
    }

    @Test
    public void reportGenerate_withOneTypeOfProduct_ok() {
        Storage.FRUIT_STORAGE.put("coconut", 60);
        String expected = FIRST_LINE + SEPARATOR
                + "coconut,60";
        String actual = reportGeneratorService.generateReport(Storage.FRUIT_STORAGE);
        assertEquals("Expected report: " + SEPARATOR + expected
                        + "but was:" + SEPARATOR + actual,
                expected, actual);
    }

    @Test
    public void reportGenerate_withoutProduct_notOk() {
        String expected = FIRST_LINE;
        String actual = reportGeneratorService.generateReport(Storage.FRUIT_STORAGE);
        assertEquals("Expected report: " + SEPARATOR + expected
                        + "but was:" + SEPARATOR + actual,
                expected, actual);
    }
}
