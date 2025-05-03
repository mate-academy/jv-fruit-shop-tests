package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ReportMakerTest {
    private static final String REPORT_HEAD = "fruit,quantity";
    private static final String SEPARATOR = ",";
    private static String actualResult;
    private static Map<String, Integer> testReportMap = new HashMap<>();
    private static ReportMaker reportMaker = new ReportMaker();

    @Before
    public void setUp() throws Exception {
        actualResult = new StringBuilder(REPORT_HEAD)
                .append(System.lineSeparator())
                .append("banana")
                .append(SEPARATOR)
                .append(50)
                .append(System.lineSeparator())
                .append("apple")
                .append(SEPARATOR)
                .append(100)
                .toString();
        testReportMap = new HashMap<>();
        testReportMap.put("apple", 100);
        testReportMap.put("banana", 50);
    }

    @Test
    public void reportMaker_correctReport_OK() {
        String expected = actualResult;
        String actual = reportMaker.make(testReportMap);
        assertEquals(actual, expected);
    }
}
