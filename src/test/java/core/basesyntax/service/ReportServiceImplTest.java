package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.interfaces.ReportService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String DEFAULT_FRUIT_NAME = "banana";
    private static final String DEFAULT_FRUIT_NAME_NEXT_LINE = "apple";
    private static final String EXPECTED_REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private static final int DEFAULT_QUANTITY = 152;
    private static final int DEFAULT_QUANTITY_NEXT_LINE = 90;
    private static final String EXPECTED_REPORT_WITH_EMPTY_DATA = "fruit,quantity"
            + System.lineSeparator()
            + System.lineSeparator();
    private static final String EXPECTED_REPORT_WITH_NULL_DATA = "fruit,quantity"
            + System.lineSeparator()
            + "null,null" + System.lineSeparator();

    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void generateReport_correctData_ok() {
        Storage.storage.put(DEFAULT_FRUIT_NAME, DEFAULT_QUANTITY);
        Storage.storage.put(DEFAULT_FRUIT_NAME_NEXT_LINE, DEFAULT_QUANTITY_NEXT_LINE);
        String actual = reportService.generateReport();
        assertEquals(EXPECTED_REPORT, actual);
    }

    @Test
    public void generateReport_emptyData_ok() {
        String actual = reportService.generateReport();
        assertEquals(actual, EXPECTED_REPORT_WITH_EMPTY_DATA);
    }

    @Test
    public void generateReport_nullData_ok() {
        Storage.storage.put(null, null);
        String actual = reportService.generateReport();
        assertEquals(actual, EXPECTED_REPORT_WITH_NULL_DATA);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
