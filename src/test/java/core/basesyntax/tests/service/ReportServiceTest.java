package core.basesyntax.tests.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.ReportServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceTest {
    private ReportService reportService;
    private Map<String, Integer> fruitStorage;

    @Before
    public void setUp() throws Exception {
        reportService = new ReportServiceImpl();
        fruitStorage = new HashMap<>();
    }

    @Test
    public void reportService_CorrectDataOneLine_Ok() {
        fruitStorage.put("banana", 120);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,120" + System.lineSeparator();
        String actual = reportService.createReport(fruitStorage);
        assertEquals(expected, actual);
    }

    @Test
    public void reportService_CorrectDataTwoLines_Ok() {
        fruitStorage.put("banana", 120);
        fruitStorage.put("apple", 1);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,120" + System.lineSeparator()
                + "apple,1" + System.lineSeparator();
        String actual = reportService.createReport(fruitStorage);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void reportService_NullData_Ok() {
        reportService.createReport(null);
    }
}
