package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportGeneratorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    public void getReport_Ok() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 50);
        storage.put("apple", 30);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,50" + System.lineSeparator()
                + "apple,30" + System.lineSeparator();
        String actual = reportGenerator.getReport(storage);
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_NullStorage_NotOk() {
        Map<String, Integer> storage = null;
        assertThrows(RuntimeException.class, () ->
                reportGenerator.getReport(storage));
    }

    @Test
    public void getReport_NullValueInStorage_NotOk() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", null);
        assertThrows(RuntimeException.class, () ->
                reportGenerator.getReport(storage));
    }
}
