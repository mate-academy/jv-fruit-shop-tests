package core.basesyntax.service.imp;

import core.basesyntax.dao.ProductDaoImp;
import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImpTest {
    private static final String HEADER = "header, of, the table";
    private static Map<String, Integer> storage;
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        storage = new HashMap<>();
        reportService = new ReportServiceImp(new ProductDaoImp(storage));
    }

    @Test
    public void createReportFromEmptyStorage_ok() {
        List<String> reportLines = reportService.createReport(HEADER);
        Assert.assertEquals(1,reportLines.size());
        Assert.assertEquals("Header: ",reportLines.get(0), HEADER);
    }

    @Test
    public void createReportFromStorage_ok() {
        storage.put("apple", 1);
        storage.put("orange", 10);
        storage.put("banana", 5);
        List<String> reportLines = reportService.createReport(HEADER);
        Assert.assertEquals("Size of list: ",storage.size() + 1, reportLines.size());
    }

    @Test
    public void createReportWithNullValueQuantity_ok() {
        storage.put("banana", null);
        List<String> reportLines = reportService.createReport(HEADER);
        Assert.assertEquals("Size of list: ",storage.size() + 1, reportLines.size());
    }

    @After
    public void tearDown() {
        storage.clear();
    }
}
