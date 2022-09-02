package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String HEADER = "fruit,quantity" + System.lineSeparator();
    private static final Map<Fruit, Integer> storage = Storage.getAll();
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void getReport_correctHeader_isValid() {
        storage.put(new Fruit("banana"), 15);
        String expected = HEADER + "banana,15" + System.lineSeparator();
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_reportHasOnlyHeader_isValid() {
        String expected = HEADER;
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_correctReport_issValid() {
        storage.put(new Fruit("banana"), 15);
        storage.put(new Fruit("apple"), 13);
        String expected = new StringBuilder().append(HEADER)
                .append("banana,15")
                .append(System.lineSeparator())
                .append("apple,13")
                .append(System.lineSeparator()).toString();
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        storage.clear();
    }
}
