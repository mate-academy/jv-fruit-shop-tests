package core.basesyntax.serviceimpl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReportServiceImplTest {
    ReportService reportService = new ReportServiceImpl();

    @Test
    public void report_EmptyDataExpectTitleRow_ok() {
        String expected = "fruit,quantity";
        String actual = reportService.newReport();
        assertEquals(actual,expected);
    }

    @Test
    public void report_PresentData_ok() {
        Storage.storage.put("banana", 152);
        Storage.storage.put("apple", 90);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        String actual = reportService.newReport();
        assertEquals(actual,expected);
    }
}