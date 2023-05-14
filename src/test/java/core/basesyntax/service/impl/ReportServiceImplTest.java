package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String SEPARATOR = ",";
    private static ReportService reportService;

    @BeforeClass
    public static void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void generate_emptyFruitsMap_Ok() {
        String expected = "fruit,quantity";
        String actual = reportService.generate();
        assertEquals(expected, actual);
    }

    @Test
    public void generate_ValidFormat_Ok() {
        Storage.fruits.put("apple",10);
        Storage.fruits.put("banana",20);
        String expected = "fruit" + SEPARATOR + "quantity" + System.lineSeparator()
                + "banana" + SEPARATOR + "20" + System.lineSeparator()
                + "apple" + SEPARATOR + "10";
        String actual = reportService.generate();
        assertEquals(expected, actual);
    }

    @After
    public void clear() {
        Storage.fruits.clear();
    }
}
