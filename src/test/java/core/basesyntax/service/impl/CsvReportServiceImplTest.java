package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportService;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class CsvReportServiceImplTest extends CsvReportServiceImpl {
    private ReportService csvReportService;
    private final String expected = "fruit,quantity" + System.lineSeparator()
            + "banana,40" + System.lineSeparator() + "apple,20";
    private final Map<String, Integer> map = Map.of(
            "banana", 40,
            "apple", 20
    );

    @Before
    public void setUp() {
        csvReportService = new CsvReportServiceImpl();
    }

    @Test
    public void createReport_ValidData_OK() {
        assertEquals(expected, csvReportService.createReport(map));
    }

    @Test(expected = RuntimeException.class)
    public void createReport_NullData_NotOK() {
        csvReportService.createReport(null);
    }
}
