package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.ReportMakerService;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ReportMakerServiceImplTest {
    private static final Map<String, Integer> EMPTY_FRUITS = new HashMap<>();
    private static final String VALID_DATA_STRING = "fruit,quantity" + System.lineSeparator()
            + "banana,172" + System.lineSeparator()
            + "apple,190";
    private static Map<String, Integer> fruits;
    private ReportMakerService reportMakerService;

    @Before
    public void setUp() {
        reportMakerService = new ReportMakerServiceImpl();
        fruits = new HashMap<>();
        fruits.put("banana", 172);
        fruits.put("apple", 190);
    }

    @Test
    public void createReportString_validData_Ok() {
        String reportString = reportMakerService.createReportString(fruits);
        assertEquals(VALID_DATA_STRING, reportString);
    }

    @Test(expected = RuntimeException.class)
    public void createReportString_nullData_notOk() {
        reportMakerService.createReportString(null);
        fail("Should throw RuntimeException when we try to create report string with null data");
    }

    @Test(expected = RuntimeException.class)
    public void createReportString_emptyList_notOk() {
        reportMakerService.createReportString(EMPTY_FRUITS);
        fail("Should throw RuntimeException when we try to create report string with empty data");
    }
}
