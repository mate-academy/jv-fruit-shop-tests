package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void writeReport_validData_ok() {
        String leftovers = "banana,152"
                + System.lineSeparator()
                + "apple,90"
                + System.lineSeparator();
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + leftovers;
        String actual = reportService.getReport(leftovers);
        assertEquals(expected, actual);
    }

    @Test
    public void writeReport_emptyData_ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportService.getReport("");
        assertEquals(expected, actual);
    }
}
