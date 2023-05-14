package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportCreatorService;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private static ReportCreatorService reportCreatorService;
    private static final String DEFAULT_TITLE = "fruit,quantity";
    private static final int TITLE_INDEX = 0;
    private static Map<String, Integer> testMap;

    @BeforeClass
    public static void setUp() {
        reportCreatorService = new ReportCreatorServiceImpl();
        testMap = new HashMap<>();
        testMap.put("banana", 44);
        testMap.put("apple", 50);
        testMap.put("orange", 123);
    }

    @Test (expected = RuntimeException.class)
    public void createReport_inputIsNull_notOk() {
        reportCreatorService.createReport(null);
    }

    @Test
    public void createReport_defaultTitle() {
        String output = reportCreatorService.createReport(testMap);
        String actualTitle = output.split(System.lineSeparator())[TITLE_INDEX];
        assertEquals(DEFAULT_TITLE, actualTitle);
    }

    @Test
    public void createReport_emptyInput_ok() {
        String actual = reportCreatorService.createReport(new HashMap<>());
        assertEquals(DEFAULT_TITLE, actual);
    }

    @Test
    public void createReport_defaultCase_ok() {
        String expected = DEFAULT_TITLE
                + System.lineSeparator()
                + "banana,44"
                + System.lineSeparator()
                + "orange,123"
                + System.lineSeparator()
                + "apple,50";
        assertEquals(expected, reportCreatorService.createReport(testMap));
    }
}
