package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReportGenerator;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private Map<String, Integer> map = new HashMap<>();
    private ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @Test
    void getReport_nullContent_notOk() {
        map.put(null, null);
        Exception exception = assertThrows(RuntimeException.class,
                () -> reportGenerator.getReport(map));
        String expected = "Wrong map value!";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void getReport_simpleReporting_ok() {
        map.put("banana",20);
        map.put("apple",50);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,50" + System.lineSeparator();
        assertEquals(expected, reportGenerator.getReport(map));
    }
}
