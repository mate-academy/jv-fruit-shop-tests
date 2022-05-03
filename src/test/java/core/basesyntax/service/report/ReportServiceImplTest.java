package core.basesyntax.service.report;

import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_validOutput_Ok() {
        Map<Fruit, Integer> storage = new HashMap<>();
        storage.put(new Fruit("banana"), 50);
        storage.put(new Fruit("apple"), 70);
        String expected = "fruit,quantity"
                + System.lineSeparator() + "banana,50"
                + System.lineSeparator() + "apple,70";
        String actual = reportService.createReport(storage.entrySet());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createReport_emptyInput_Ok() {
        Map<Fruit, Integer> emptyMap = new HashMap<>();;
        String expected = "fruit,quantity";
        String actual = reportService.createReport(emptyMap.entrySet());
        Assert.assertEquals(expected, actual);
    }
}
