package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReporterImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReporterTest {
    private static final String REPORT_HEAD = "fruit,quantity" + System.lineSeparator();
    private static final Map<String,Integer> EMPTY_STORAGE = new HashMap<>();
    private static Reporter reporter;

    @BeforeClass
    public static void setUp() {
        reporter = new ReporterImpl();
    }

    @Test
    public void createReport_correctDataTest_Ok() {
        Map<String, Integer> storage = new TreeMap<>();
        storage.put("Apple", 100);
        storage.put("Banana", 30);
        storage.put("Orange", 55);
        String expected = REPORT_HEAD
                + "Apple,100" + System.lineSeparator()
                + "Banana,30" + System.lineSeparator()
                + "Orange,55";
        String actual = reporter.createReport(storage);
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_emptyStorageTest_Ok() {
        String actual = reporter.createReport(EMPTY_STORAGE);
        assertEquals(REPORT_HEAD, actual);
    }

    @Test(expected = RuntimeException.class)
    public void createReporter_nullTest_notOk() {
        reporter.createReport(null);
    }
}
