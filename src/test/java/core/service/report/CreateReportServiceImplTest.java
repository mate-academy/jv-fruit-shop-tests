package core.service.report;

import core.db.Storage;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreateReportServiceImplTest {
    private static final String TITLE = "fruit,quantity";
    private CreateReportService reportService;

    @Before
    public void setUp() throws Exception {
        reportService = new CreateReportServiceImpl();
    }

    @Test
    public void createReport_Ok() {
        Storage.getStockStorage().put("banana", 200);
        Storage.getStockStorage().put("apple", 100);
        ArrayList expectedReport = new ArrayList();
        expectedReport.add(TITLE);
        expectedReport.add("banana,200");
        expectedReport.add("apple,100");
        Assert.assertEquals(expectedReport, reportService.createReport());
    }

    @Test
    public void createReport_NotOk() {
        Storage.getStockStorage().put("banana", 200);
        Storage.getStockStorage().put("apple", 100);
        ArrayList expectedReport = new ArrayList();
        expectedReport.add(TITLE);
        expectedReport.add("banana,200");
        expectedReport.add("orange,100");
        Assert.assertNotEquals(expectedReport, reportService.createReport());
    }

    @Test
    public void createReportWithNull_NotOk() {
        Storage.getStockStorage().put(null, 200);
        Storage.getStockStorage().put("apple", 100);
        ArrayList expectedReport = new ArrayList();
        expectedReport.add(TITLE);
        expectedReport.add("banana,200");
        expectedReport.add("orange,100");
        Assert.assertNotEquals(expectedReport, reportService.createReport());
    }
}
