package service.impl;

import static org.junit.Assert.assertEquals;

import db.Storage;
import java.util.List;
import model.Fruit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import service.ReportService;

public class ReportServiceImplTest {
    private static ReportService<String> reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.storage.clear();
    }

    @Test
    public void createReport_storageIsEmpty_ok() {
        List<String> expected = List.of("fruit,quantity");
        assertEquals(expected, reportService.createReport());
    }

    @Test
    public void createReport_storageContainsOneElement_ok() {
        Storage.storage.put(new Fruit("banana"), 100);
        List<String> expected = List.of("fruit,quantity", "banana,100");
        assertEquals(expected, reportService.createReport());
    }

    @Test
    public void createReport_storageContainsManyElements_ok() {
        Storage.storage.put(new Fruit("banana"), 10);
        Storage.storage.put(new Fruit("apple"), 20);
        List<String> expected = List.of("fruit,quantity",
                "banana,10",
                "apple,20");
        assertEquals(expected, reportService.createReport());
    }
}
