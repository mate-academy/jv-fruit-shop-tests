package core.basesyntax.service.cvs;

import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CsvReportServiceImplTest {
    private CsvReportService csvReportService;

    @Before
    public void setUp() {
        csvReportService = new CsvReportServiceImpl();
    }

    @Test
    public void getReport_isOk() {
        Storage.fruits.put("apple", 25);
        Storage.fruits.put("banana", 10);
        Storage.fruits.put("orange", 9);
        String actual = csvReportService.getReport(Storage.fruits);
        assertTrue(actual.startsWith("fruit,quantity"));
        assertTrue(actual.contains("apple,25"));
        assertTrue(actual.contains("banana,10"));
        assertTrue(actual.contains("orange,9"));
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
