package core.basesyntax.dao;

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
        fruitsStorage.put("banana", 30);
        fruitsStorage.put("apple", 40);
        String expected = COLUMN_NAMES + System.lineSeparator() + "banana,30"
                + System.lineSeparator() + "apple,40" + System.lineSeparator();
        String actual = reportService.createReport(fruitsStorage);
        Assert.assertEquals("Report wasn't created correctly", expected, actual);
    }
}
