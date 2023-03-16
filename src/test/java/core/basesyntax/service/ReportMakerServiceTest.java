package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReportMakerServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class ReportMakerServiceTest {
    private static final Map<String, Integer> BASE = new HashMap<>();

    private static final ReportMakerService REPORT_MAKER = new ReportMakerServiceImpl();

    @Test
    public void reporterMaker_report_ok() {
        BASE.put("banana", 5);
        BASE.put("apple", 25);
        String expected = "fruit, quantity" + System.lineSeparator()
                + "banana, 5" + System.lineSeparator()
                + "apple, 25";
        String actual = REPORT_MAKER.report(BASE);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void reporterMaker_report_InputIsEmpty_notOk() {
        REPORT_MAKER.report(BASE);
    }
}
