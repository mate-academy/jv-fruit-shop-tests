package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReportMakerServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMakerServiceTest {
    private static Map<String, Integer> base;
    private static ReportMakerService reportMaker;

    @BeforeClass
    public static void initialize() {
        base = new HashMap<>();
        reportMaker = new ReportMakerServiceImpl();
    }

    @Test
    public void report_normalInput_ok() {
        base.put("banana", 5);
        base.put("apple", 25);
        String expected = "fruit, quantity" + System.lineSeparator()
                + "banana, 5" + System.lineSeparator()
                + "apple, 25";
        String actual = reportMaker.report(base);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void report_InputIsEmpty_notOk() {
        reportMaker.report(base);
    }
}
