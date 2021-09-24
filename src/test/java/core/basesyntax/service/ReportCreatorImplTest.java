package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Storage;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ReportCreatorImplTest {
    private final ReportCreator reportCreator = new ReportCreatorImpl();

    @Test
    public void createReport_validInput_ok() {
        Storage.FRUIT_COUNT.put("apple", 100);
        Storage.FRUIT_COUNT.put("orange", 150);
        List<String> expected = new ArrayList<>();
        expected.add("orange,150");
        expected.add("apple,100");
        List<String> actual = reportCreator.createReport();
        assertEquals("Method should return: " + expected + " but was: " + actual,
                expected, actual);
        Storage.FRUIT_COUNT.clear();
    }

    @Test
    public void createReport_emptyInput_ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = reportCreator.createReport();
        assertEquals("Method should return empty list for empty input",
                expected, actual);
        Storage.FRUIT_COUNT.clear();
    }

    @Test
    public void createReport_nullInput_ok() {
        Storage.FRUIT_COUNT.put(null, null);
        List<String> expected = new ArrayList<>();
        expected.add("null,null");
        List<String> actual = reportCreator.createReport();
        assertEquals("Method should return list with null for null input",
                expected, actual);
        Storage.FRUIT_COUNT.clear();
    }
}
