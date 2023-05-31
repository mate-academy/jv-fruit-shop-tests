package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static Map<String, Integer> storageMap;
    private ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
        storageMap = new HashMap<>();
    }

    @Test
    public void prepareReport_ok() {
        storageMap.put("banana", 152);
        storageMap.put("apple", 90);
        Set<Map.Entry<String, Integer>> entries = storageMap.entrySet();
        String expected = "banana,152" + System.lineSeparator() + "apple,90";
        String actual = reportService.prepareReport(entries);
        assertEquals(expected, actual);
    }

    @Test
    public void prepareReport_empty_ok() {
        String expected = "";
        String actual = reportService.prepareReport(storageMap.entrySet());
        assertEquals(expected, actual);
    }
}
