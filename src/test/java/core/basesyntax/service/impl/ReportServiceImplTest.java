package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final Map<String, Integer> CORRECT_STORAGE = Map.of(
            "banana", 10,
            "apple", 10,
            "wine", 10);

    private static final String EXPECTED_CORRECT_REPORT = HEADER + LINE_SEPARATOR
            + "apple,10" + LINE_SEPARATOR
            + "banana,10" + LINE_SEPARATOR
            + "wine,10";

    private static final String EXPECTED_REPORT_FOR_EMPTY_STORAGE = "Storage is empty";
    private static final Map<String, Integer> EMPTY_STORAGE = new HashMap<>();
    private ReportService reportService;

    @Test
    public void getReport_storageWithCorrectData_ok() {
        reportService = new ReportServiceImpl(CORRECT_STORAGE);
        assertEquals("Test failed! Expected report with the following CSV format:"
                        + LINE_SEPARATOR + EXPECTED_CORRECT_REPORT + LINE_SEPARATOR
                        + "But actual is:" + LINE_SEPARATOR + reportService.getReport(),
                EXPECTED_CORRECT_REPORT, reportService.getReport());
    }

    @Test
    public void getReport_emptyStorage_ok() {
        reportService = new ReportServiceImpl(EMPTY_STORAGE);
        assertEquals("Test failed! Expected report with the following message: \""
                        + EXPECTED_REPORT_FOR_EMPTY_STORAGE + "\" But actual is: \""
                        + reportService.getReport() + '\"',
                EXPECTED_REPORT_FOR_EMPTY_STORAGE, reportService.getReport());
    }
}
