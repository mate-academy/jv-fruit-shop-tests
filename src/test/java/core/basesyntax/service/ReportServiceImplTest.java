package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static final String START_MESSAGE = "fruit,quantity";

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void report_validValue_Ok() {
        Storage.storage.put(new Fruit("banana"),200);
        Storage.storage.put(new Fruit("apple"),100);
        String expected = START_MESSAGE
                + System.lineSeparator() + "banana,200"
                + System.lineSeparator() + "apple,100";
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }

    @Test
    public void report_emptyStorage_Ok() {
        String actual = reportService.getReport();
        assertEquals(START_MESSAGE, actual);
    }

    @Test (expected = NullPointerException.class)
    public void report_nullInStorage_notOk() {
        Storage.storage.put(null,null);
        reportService.getReport();
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
