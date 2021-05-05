package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @After
    public void tearDown() {
        Storage.fruitsContainer.clear();
    }

    @Test
    public void createReport_withCorrectData_isOk() {
        Storage.fruitsContainer.put("apple", 100);
        String actual = reportService.createReport(Storage.fruitsContainer);
        String expected = "fruit,quantity\napple,100";
        assertEquals(expected,actual);
    }

    @Test
    public void createReport_withEmptyMap_isOk() {
        String actual = reportService.createReport(Storage.fruitsContainer);
        String expected = "fruit,quantity";
        assertEquals(expected, actual);
    }
}
