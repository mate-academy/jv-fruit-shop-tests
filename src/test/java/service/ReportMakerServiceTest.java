package service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import service.impl.ReportMakerServiceImpl;

public class ReportMakerServiceTest {
    private static final String FIRST_LINE_REPORT = "fruits,quantity";
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String LEMON = "lemon";
    private static final int BANANA_QUANTITY = 10;
    private static final int APPLE_QUANTITY = 20;
    private static final int LEMON_QUANTITY = 30;
    private static final String COLUMN_SEPARATOR = ",";
    private static ReportMakerService reportMakerService;

    @BeforeClass
    public static void beforeAll() {
        reportMakerService = new ReportMakerServiceImpl();
    }

    @Test
    public void standart_case_ok() {
        Map<String, Integer> map = new HashMap<>();
        map.put(BANANA, BANANA_QUANTITY);
        map.put(APPLE, APPLE_QUANTITY);
        map.put(LEMON, LEMON_QUANTITY);

        String expected = new StringBuilder()
                .append(FIRST_LINE_REPORT).append(System.lineSeparator())
                .append(BANANA).append(COLUMN_SEPARATOR).append(BANANA_QUANTITY)
                .append(System.lineSeparator())
                .append(APPLE).append(COLUMN_SEPARATOR).append(APPLE_QUANTITY)
                .append(System.lineSeparator())
                .append(LEMON).append(COLUMN_SEPARATOR).append(LEMON_QUANTITY)
                .toString();

        assertEquals(expected,reportMakerService.makeReport(map));
    }

    @Test(expected = RuntimeException.class)
    public void makeReport_nullArgument_notOk() {
        reportMakerService.makeReport(null);
    }

    @Test
    public void makeReport_emptyMap_ok() {
        reportMakerService.makeReport(new HashMap<>());
    }
}
