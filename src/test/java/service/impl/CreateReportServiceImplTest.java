package service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import db.Storage;
import org.junit.Before;
import org.junit.Test;

public class CreateReportServiceImplTest {
    private static final String reportForTest = new StringBuilder().append("fruit,quantity")
            .append(System.lineSeparator())
            .append("banana,152")
            .append(System.lineSeparator())
            .append("apple,90")
            .append(System.lineSeparator())
            .toString();
    private static CreateReportServiceImpl createReportServiceImpl;

    @Before
    public void setUp() {
        createReportServiceImpl = new CreateReportServiceImpl();
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
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
