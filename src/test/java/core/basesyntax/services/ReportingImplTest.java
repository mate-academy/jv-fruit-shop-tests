package core.basesyntax.services;

import static org.junit.Assert.assertEquals;

import core.basesyntax.storage.Stock;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Test;

public class ReportingImplTest {
    private static final String START_MESSAGE = "fruit,quantity";

    @After
    public void tearDown() throws Exception {
        Stock.stockStorage.clear();
    }

    @Test
    public void createReport_NotNullData_Ok() {
        Stock.stockStorage.put("banana", 50);
        Stock.stockStorage.put("apple", 20);
        List<String> expected = new ArrayList<>();
        expected.add(START_MESSAGE);
        expected.add("banana,50");
        expected.add("apple,20");
        Reporting reporting = new ReportingImpl();
        List<String> actual = reporting.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_Null_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add(START_MESSAGE);
        Reporting reporting = new ReportingImpl();
        List<String> actual = reporting.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_NullValues_Ok() {
        Stock.stockStorage.put(null, null);
        List<String> expected = new ArrayList<>();
        expected.add(START_MESSAGE);
        expected.add("null,null");
        Reporting reporting = new ReportingImpl();
        List<String> actual = reporting.createReport();
        assertEquals(expected, actual);
    }
}
