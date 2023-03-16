package service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import db.Storage;
import org.junit.Before;
import org.junit.Test;

public class CreateReportServiceImplTest {
    private static final String TITLE = "fruit,quantity";
    private static final String SECOND_LINE_OF_REPORT = "banana,152";
    private static final String THIRD_LINE_OF_REPORT = "apple,90";
    private static final String BANANA_FRUIT = "banana";
    private static final String APPLE_FRUIT = "apple";
    private static final Integer APPLE_QUANTITY = 90;
    private static final Integer BANANA_QUANTITY = 152;

    private static final String reportForTest = new StringBuilder().append(TITLE)
            .append(System.lineSeparator())
            .append(SECOND_LINE_OF_REPORT)
            .append(System.lineSeparator())
            .append(THIRD_LINE_OF_REPORT)
            .append(System.lineSeparator())
            .toString();
    private static CreateReportServiceImpl createReportServiceImpl;

    @Before
    public void setUp() {
        createReportServiceImpl = new CreateReportServiceImpl();
        Storage.fruits.put(BANANA_FRUIT, BANANA_QUANTITY);
        Storage.fruits.put(APPLE_FRUIT, APPLE_QUANTITY);
    }

    @Test
    public void report_CreateReport_Ok() {
        String actual = createReportServiceImpl.report();
        assertEquals(reportForTest, actual);
    }

    @Test
    public void report_CreateEmptyReport_NotOk() {
        Storage.fruits.clear();
        String actual = createReportServiceImpl.report();
        assertNotEquals(reportForTest, actual);
    }
}
