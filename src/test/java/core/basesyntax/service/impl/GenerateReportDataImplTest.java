package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.GenerateReportDataService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class GenerateReportDataImplTest {
    private GenerateReportDataService generateReportDataService = new GenerateReportDataImpl();
    private List<String> data = new ArrayList<>();

    @Test
    public void generateReport_validDate_Ok() {
        String value = "banana,152";
        data.add(value);
        String actual = generateReportDataService.report(data);
        String expected = "fruit,quality"
                + System.lineSeparator()
                + value
                + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void generateReport_emptyData_Ok() {
        String actual = generateReportDataService.report(data);
        String expected = "fruit,quality"
                + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void generateReport_nullData_notOk() {
        generateReportDataService.report(null);
    }
}
