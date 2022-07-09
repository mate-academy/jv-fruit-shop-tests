package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceTest {
    private static final Map<String, Integer> testMap = new HashMap<>(3);
    private static final List<String> expected = new ArrayList<>();
    private static final String testFileHeader = "fruit,quantity";

    @BeforeClass
    public static void beforeClass() throws Exception {
        testMap.put("banana", 30);
        testMap.put("apple", 50);
        testMap.put("pineapple", 22);
        expected.add(testFileHeader);
        expected.add(System.lineSeparator());
        expected.add("banana" + "," + "30");
        expected.add(System.lineSeparator());
        expected.add("pineapple" + "," + "22");
        expected.add(System.lineSeparator());
        expected.add("apple" + "," + "50");
    }

    @Test
    public void reportCreatorService_ListValuesMatch_OK() {
        ReportCreatorService reportCreatorService = new ReportCreatorServiceImpl();
        List<String> testList = reportCreatorService.createReport(testMap);
        assertEquals(testList, expected);
    }

    @Test(expected = NullPointerException.class)
    public void reportMap_null_NotOK() {
        Map<String, Integer> testMap1 = null;
        ReportCreatorService reportCreatorService = new ReportCreatorServiceImpl();
        reportCreatorService.createReport(testMap1);
    }
}
