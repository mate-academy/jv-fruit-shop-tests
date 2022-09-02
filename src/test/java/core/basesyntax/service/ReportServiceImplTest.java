package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void reportService_createReport_OK() {
        Map<Fruit, Integer> actualMap = new HashMap<>();
        actualMap.put(new Fruit("banana"), 55);
        actualMap.put(new Fruit("apple"), 66);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,55" + System.lineSeparator() + "apple,66";
        String actual = reportService.createReport(actualMap.entrySet());
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void reportService_createReportNullData_NotOk() {
        reportService.createReport(null);
    }

    @Test
    public void reportService_createReportEmptyData_OK() {
        Map<Fruit, Integer> actualMap = new HashMap<>();
        String actual = reportService.createReport(actualMap.entrySet());
        String expected = "fruit,quantity";
        assertEquals(expected, actual);
    }
}
