package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.impl.ReportServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void makeReport_correctInput_ok() {
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
        assertEquals("Incorrect output", expected, reportService.makeReport(map));
    }

    @Test(expected = RuntimeException.class)
    public void makeReport_nullArgument_notOk() {
        reportService.makeReport(null);
        fail("Expected an exception to be thrown");
    }

    @Test
    public void makeReport_emptyInput_ok() {
        assertEquals("", reportService.makeReport(new HashMap<>()));
    }
}
