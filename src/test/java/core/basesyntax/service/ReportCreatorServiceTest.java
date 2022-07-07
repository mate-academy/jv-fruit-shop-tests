package core.basesyntax.service;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class ReportCreatorServiceTest {

    @Test
    public void reportCreatorService_createList_OK() {
        final Map<String, Integer> testMap = new HashMap<>(3);
        testMap.put("banana", 30);
        testMap.put("apple", 50);
        testMap.put("pinapple", 22);
        ReportCreatorService reportCreatorService = new ReportCreatorServiceImpl();
        List<String> testList = reportCreatorService.createReport(testMap);
        assertFalse(testList.isEmpty());
    }

    @Test
    public void reportCreatorService_ListValuesMatch_OK() {
        final String testFileHeader = "fruit,quantity";
        final Map<String, Integer> testMap = new HashMap<>(3);
        testMap.put("banana", 30);
        testMap.put("apple", 50);
        testMap.put("pineapple", 22);
        List<String> expected = new ArrayList<>();
        expected.add(testFileHeader);
        expected.add(System.lineSeparator());
        expected.add("banana" + "," + "30");
        expected.add(System.lineSeparator());
        expected.add("pineapple" + "," + "22");
        expected.add(System.lineSeparator());
        expected.add("apple" + "," + "50");
        ReportCreatorService reportCreatorService = new ReportCreatorServiceImpl();
        List<String> testList = reportCreatorService.createReport(testMap);
        assertEquals(testList, expected);
    }
}
