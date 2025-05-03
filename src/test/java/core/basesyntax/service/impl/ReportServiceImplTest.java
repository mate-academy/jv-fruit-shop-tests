package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String RESULT = "fruit,quantity" + System.lineSeparator()
            + "banana,107" + System.lineSeparator()
            + "apple,10" + System.lineSeparator();
    private static ReportService reportCreated;

    @BeforeClass
    public static void beforeClass() {
        reportCreated = new ReportServiceImpl();
        Storage.storage.put("banana", 107);
        Storage.storage.put("apple", 10);
    }

    @Test
    public void createReportFrom_correctData_isOk() {
        String actual = reportCreated.createReport();
        assertEquals("Expected report: " + RESULT + ", but was: "
                + actual, actual, RESULT);
    }
}
