package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.impl.ReporterImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;

public class ReporterTest {
    private static final String REPORT_HEAD = "fruit,quantity" + System.lineSeparator();
    private Reporter reporter = new ReporterImpl();

    @Test
    public void createReport_Ok() {
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
    public void emptyStorageCreateReport_Ok() {
        Map<String, Integer> storage = new HashMap<>();
        String actual = reporter.createReport(storage);
        assertEquals(REPORT_HEAD, actual);
    }

    @Test public void nullStorageCreateReport_NotOk() {
        Map<String, Integer> storage = null;
        try {
            String actual = reporter.createReport(storage);
        } catch (RuntimeException e) {
            return;
        }
        fail("Test failed! Reporter should be thrown runtime exception: \"Can't create report!\"");
    }
}
