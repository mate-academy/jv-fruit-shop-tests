package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void createReport_validData_ok() {
        Storage.storage.put(new Fruit("banana"), 100);
        Storage.storage.put(new Fruit("apple"), 20);
        List<String> expected = List.of("fruit,quantity", "banana,100", "apple,20");
        List<String> actual = reportService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createReport_emptyStorage_ok() {
        List<String> expected = List.of("fruit,quantity");
        List<String> actual = reportService.createReport();
        Assert.assertEquals(expected, actual);
    }
}
