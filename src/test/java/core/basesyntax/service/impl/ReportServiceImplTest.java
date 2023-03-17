package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String RESULT_FOR_EMPTY_STORAGE = "fruit,quantity";
    private static final String RESULT_FOR_FULL_STORAGE = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,90";
    private static final String TITLE_FOR_BANANAS = "banana";
    private static final String TITLE_FOR_APPLES = "apple";
    private static final Integer QUANTITY_FOR_BANANAS = 152;
    private static final Integer QUANTITY_FOR_APPLES = 90;
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void makeReport_emptyStorage_ok() {
        String expected = RESULT_FOR_EMPTY_STORAGE;
        String actual = reportService.makeReport();
        assertEquals(expected, actual);
    }

    @Test
    public void makeReport_fullStorage_ok() {
        Storage.fruits.put(TITLE_FOR_BANANAS, QUANTITY_FOR_BANANAS);
        Storage.fruits.put(TITLE_FOR_APPLES, QUANTITY_FOR_APPLES);
        String expected = RESULT_FOR_FULL_STORAGE;
        String actual = reportService.makeReport();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
