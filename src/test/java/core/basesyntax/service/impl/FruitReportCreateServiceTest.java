package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreateService;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitReportCreateServiceTest {
    private static ReportCreateService reportCreateService;

    @BeforeClass
    public static void beforeClass() {
        reportCreateService = new FruitReportCreateService();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void createReport_ok() {
        Map<String, Integer> data = new HashMap<>();
        data.put("banana", 199);
        data.put("apple", 140);
        Storage.fruits.putAll(data);

        String expected = "fruit,quantity"
                + System.lineSeparator() + "banana,199"
                + System.lineSeparator() + "apple,140";
        String actual = reportCreateService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_emptyStorage_ok() {
        String expected = "fruit,quantity";
        String actual = reportCreateService.createReport();
        assertEquals(expected, actual);
    }
}
