package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String TEST_FRUIT = "apple";
    private static final int TEST_QUANTITY = 100;
    private static final String TITLE_REPORT = "fruit,quantity";
    private static final String COMMA = ",";
    private ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        Storage.fruits.put(TEST_FRUIT, TEST_QUANTITY);
    }

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void getReport_validData_ok() {
        String expected = TITLE_REPORT + System.lineSeparator() + TEST_FRUIT
                + COMMA + String.valueOf(TEST_QUANTITY) + System.lineSeparator();
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
