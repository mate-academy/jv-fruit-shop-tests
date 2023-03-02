package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportMakerService;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMakerServiceImplTest {

    private static ReportMakerService reportMakerService;

    @BeforeClass
    public static void setUp() {
        reportMakerService = new ReportMakerServiceImpl();
    }

    @Test
    public void make_defaultCase_ok() {
        Map<String, Integer> map = new HashMap<>();
        map.put("banana", 1);
        map.put("apple", 2);
        map.put("pineapple", 3);

        String expected = new StringBuilder().append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,1")
                .append(System.lineSeparator())
                .append("apple,2")
                .append(System.lineSeparator())
                .append("pineapple,3").append(System.lineSeparator()).toString();

        assertEquals(reportMakerService.make(map), expected);
    }

    @Test(expected = RuntimeException.class)
    public void make_nullArgument_notOk() {
        reportMakerService.make(null);
    }

    @Test
    public void make_emptyMap_ok() {
        assertEquals(reportMakerService.make(new HashMap<>()), "");
    }
}
