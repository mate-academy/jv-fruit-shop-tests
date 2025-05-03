package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceTest {
    private static final Map<String, Integer> testMap = new HashMap<>();
    private static final List<String> expected = new ArrayList<>();
    private static ReportCreatorService reportCreatorService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        testMap.put("banana", 30);
        testMap.put("apple", 50);
        testMap.put("pineapple", 22);
        expected.add("fruit,quantity");
        expected.add(System.lineSeparator());
        expected.add("banana" + "," + "30");
        expected.add(System.lineSeparator());
        expected.add("apple" + "," + "50");
        expected.add(System.lineSeparator());
        expected.add("pineapple" + "," + "22");
    }

    @Before
    public void setUp() throws Exception {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @Test
    public void createReport_OK() {
        List<String> testList = reportCreatorService.createReport(testMap);
        assertEquals(testList, expected);
    }

    @Test(expected = RuntimeException.class)
    public void createReport_nullReportMap_NotOK() {
        reportCreatorService.createReport(null);
    }

    @Test(expected = RuntimeException.class)
    public void reportMap_contains_null_NotOK() {
        Map<String, Integer> testMap2 = new HashMap<>();
        testMap2.put(null, null);
        reportCreatorService.createReport(testMap2);
    }
}
