package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.impl.CsvReportServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;
    private static final String REPORT_HEADER = "fruit,quantity";
    private static final String COMMA_CHARACTER = ",";

    @BeforeClass
    public static void beforeClass() {
        reportService = new CsvReportServiceImpl();
    }

    @Test
    public void createReport_Ok() {
        Storage.fruitsStorage.put("apple",65);
        Storage.fruitsStorage.put("banana",120);
        Storage.fruitsStorage.put("orange",45);
        String actual = reportService.createReport(Storage.fruitsStorage);
        assertTrue(actual.contains(REPORT_HEADER)
                && actual.contains("apple" + COMMA_CHARACTER + 65)
                && actual.contains("banana" + COMMA_CHARACTER + 120)
                && actual.contains("orange" + COMMA_CHARACTER + 45));
    }
}
