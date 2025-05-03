package core.basesyntax.service.report;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static final Map<String, Integer> storage = new HashMap<>();
    private static final String EXPECTED =
            "fruit,quantity" + System.lineSeparator()
                    + "banana,100" + System.lineSeparator()
                    + "apple,100";
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        storage.put("banana",100);
        storage.put("apple",100);
        reportService = new ReportServiceImpl();
    }

    @Test
    public void formReport_validData_ok() {
        String actual = reportService.formReport(storage.entrySet());
        Assert.assertEquals(EXPECTED, actual);
    }

    @Test
    public void formReport_emptyData_notOk() {
        Map<String, Integer> emptyStorage = new HashMap<>();
        String actual = reportService.formReport(emptyStorage.entrySet());
        String expected = "fruit,quantity";
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void formReport_nullData_notOk() {
        reportService.formReport(null);
    }

}
