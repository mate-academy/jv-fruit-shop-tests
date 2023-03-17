package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReportServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void makeReport_correctInput_ok() {
        Map<String, Integer> map = new HashMap<>();
        map.put("banana", 100);
        map.put("apple", 100);
        map.put("grenade", 100);

        String expected = new StringBuilder().append("fruits,quantity")
                .append(System.lineSeparator())
                .append("banana,100")
                .append(System.lineSeparator())
                .append("apple,100")
                .append(System.lineSeparator())
                .append("grenade,100").append(System.lineSeparator()).toString();
        assertEquals(reportService.makeReport(map), expected);
    }

    @Test
    void makeReport_nullArgument_notOk() {
        assertThrows(RuntimeException.class, () -> {
            reportService.makeReport(null);
        });
    }

    @Test
    void makeReport_emptyInput_ok() {
        assertEquals(reportService.makeReport(new HashMap<>()), "");
    }
}
