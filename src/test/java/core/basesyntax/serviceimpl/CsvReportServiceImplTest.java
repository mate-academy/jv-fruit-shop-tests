package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.Before;
import org.junit.Test;

public class CsvReportServiceImplTest {
    private static final String FIRST_LINE = "fruit,quantity";
    private ReportService reportService;

    @Before
    public void setUp() {
        reportService = new CsvReportServiceImpl();
    }

    @Test
    public void creatReport_Ok() {
        String expected = new StringBuilder(FIRST_LINE).append("\n").append("banana,152")
                        .append("\n").append("apple,90").toString();
        Storage.fruitsStorage.put("banana", 152);
        Storage.fruitsStorage.put("apple", 90);
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void creatReportWithEmptyList_Ok() {
        String expected = FIRST_LINE;
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }
}
