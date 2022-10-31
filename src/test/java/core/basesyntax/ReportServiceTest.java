package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;

    @BeforeClass
    public static void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void reportService_generateReportFromNotEmptyStorage_ok() {
        Storage.storageContents.put("banana", 152);
        Storage.storageContents.put("apple", 90);
        Storage.storageContents.put("oranges", 270);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator()
                + "oranges,270";
        String actual = reportService.generate();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void reportService_generateReportFromEmptyStorage_notOk() {
        String report = reportService.generate();
    }
}
