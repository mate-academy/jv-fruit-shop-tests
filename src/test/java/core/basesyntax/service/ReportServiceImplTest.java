package core.basesyntax.service;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String COLUMN_NAMES = "fruit,quantity";
    private static ReportService reportService;

    @BeforeClass
    public static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_correctData_ok() {
        Map<String, Integer> fruitsStorage = new HashMap<>();
        fruitsStorage.put("banana", 20);
        fruitsStorage.put("apple", 15);
        String expected = COLUMN_NAMES + "\r\nbanana,20\r\napple,15\r\n";
        String actual = reportService.createReport(fruitsStorage);
        Assert.assertEquals("Report wasn't created correctly", expected, actual);
    }
}
