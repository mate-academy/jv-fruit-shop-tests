package service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import storage.DataStorage;

public class ReportServiceImplTest {
    private String expectedReport;

    @Before
    public void clear() {
        DataStorage.FRUIT_MAP.clear();
    }

    @Test
    public void getReport_emptyStorage_ok() {
        expectedReport = "fruit,quantity" + System.lineSeparator();
        String actualReport = new ReportServiceImpl().getReport();
        assertEquals("Report creation error for empty storage!",
                expectedReport, actualReport);
    }

    @Test
    public void getReport_ok() {
        DataStorage.FRUIT_MAP.put("banana",152);
        DataStorage.FRUIT_MAP.put("apple",90);
        expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        String actualReport = new ReportServiceImpl().getReport();
        assertEquals("Report creation error for empty storage!",
                expectedReport, actualReport);
    }
}
