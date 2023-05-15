package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String EXPECTED_DATA = "fruit,quantity\n"
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private final ReportService reportService = new ReportServiceImpl();

    @Before
    public void setUp() {
        Storage.fruit.put("banana", 152);
        Storage.fruit.put("apple", 90);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruit.clear();
    }

    @Test
    public void report_correctData_ok() {
        String actual = reportService.report();
        assertEquals(EXPECTED_DATA, actual);
    }
}
