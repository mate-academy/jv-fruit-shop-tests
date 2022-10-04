package core.basesyntax.tests.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.ReportServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class ReportTest {
    private ReportService reportService = new ReportServiceImpl();

    @Test
    public void reportService_CorrectDataOneLine_Ok() {
        Map<String, Integer> fruitStorage = new HashMap<>();
        fruitStorage.put("banana", 120);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,120" + System.lineSeparator();
        assertEquals(expected, reportService.createReport(fruitStorage));
    }

    @Test
    public void reportService_CorrectDataTwoLines_Ok() {
        Map<String, Integer> fruitStorage = new HashMap<>();
        fruitStorage.put("banana", 120);
        fruitStorage.put("apple", 1);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,120" + System.lineSeparator()
                + "apple,1" + System.lineSeparator();
        assertEquals(expected, reportService.createReport(fruitStorage));
    }

    @Test (expected = RuntimeException.class)
    public void reportService_NullData_Ok() {
        reportService.createReport(null);
    }
}
