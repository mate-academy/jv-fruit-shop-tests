package core.basesyntax.service.report;

import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static final Map<Fruit, Integer> emptyMap = new HashMap<>();
    private static final Map<Fruit, Integer> storage = new HashMap<>();
    private static ReportService reportService;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String EXPECTED = "fruit,quantity"
            + LINE_SEPARATOR + "banana,50" + LINE_SEPARATOR + "apple,70";

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
        storage.put(new Fruit("banana"), 50);
        storage.put(new Fruit("apple"), 70);
    }

    @Test
    public void createReport_validOutput_Ok() {
        String actual = reportService.createReport(storage.entrySet());
        Assert.assertEquals(EXPECTED, actual);
    }

    @Test
    public void createReport_emptyInput_NotOk() {
        String expected = "fruit,quantity";
        String actual = reportService.createReport(emptyMap.entrySet());
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void createReport_null_NotOk() {
        reportService.createReport(null);
    }
}
