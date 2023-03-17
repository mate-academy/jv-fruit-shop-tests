package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void report_emptyDataExpectTitleRow_ok() {
        String expected = "fruit,quantity";
        String actual = reportService.newReport();
        assertEquals(actual,expected);
    }

    @Test
    public void report_presentData_ok() {
        Storage.storage.put("banana", 152);
        Storage.storage.put("apple", 90);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        String actual = reportService.newReport();
        assertEquals(actual,expected);
    }
}
