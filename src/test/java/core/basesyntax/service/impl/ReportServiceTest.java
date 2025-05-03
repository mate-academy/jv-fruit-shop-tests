package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;
    private static Storage storage;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportService = new ReportServiceImpl();
        storage = new Storage();
    }

    @Test
    public void createReport_Ok() {
        storage.getFruitsStorage().put("banana", 120);
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("fruit,quantity");
        expectedResult.add("banana,120");
        List<String> actualResult = reportService.createReport(storage.getFruitsStorage());
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void createReport_emptyStorage_notOk() {
        reportService.createReport(storage.getFruitsStorage());
    }

    @After
    public void tearDown() {
        storage.getFruitsStorage().clear();
    }
}
