package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static final StringBuilder EXPECTED = new StringBuilder("fruit,balance")
            .append(System.lineSeparator())
            .append("banana,50")
            .append(System.lineSeparator())
            .append("apple,70");
    private static final Map<Fruit, Integer> storage = new HashMap<>();
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
        storage.put(new Fruit("banana"), 50);
        storage.put(new Fruit("apple"), 70);
    }

    @Test
    public void makeReport_validOutput_Ok() {
        String actual = reportService.makeReport(storage.entrySet()).toString();
        assertEquals(EXPECTED.toString(), actual);
    }

    @Test (expected = NullPointerException.class)
    public void makeReport_nullStorage_NotOk() {
        reportService.makeReport(null);
    }

    @Test
    public void makeReport_emptyInput_NotOk() {
        Map<Fruit, Integer> emptyMap = new HashMap<>();
        String expected = "fruit,balance";
        String actual = reportService.makeReport(emptyMap.entrySet()).toString();
        assertEquals(expected, actual);
    }
}
