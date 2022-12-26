package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.servise.ReportService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ReportServiceImplTest {
    private static final String FIRST_LINE = "fruit, quantity";
    private static ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
        Storage.storageMap.put(new Fruit("avocado"),40);
        Storage.storageMap.put(new Fruit("banana"),43);
        Storage.storageMap.put(new Fruit("apple"),185);
    }

    @Test
    public void report_Storage_IsNotEmptyIsOk() {
        Assertions.assertTrue(Storage.storageMap.size() > 0,
                "Storage is not Empty");
    }

    @Test
    public void report_Storage_IsEmptyIsNotOk() {
        Storage.storageMap.clear();
        Assertions.assertTrue(Storage.storageMap.isEmpty(), "Storage is Empty");
    }

    @Test
    public void report_Storage_IsNull() {
        Storage.storageMap.clear();
        Storage.storageMap.put(null,null);
        Assertions.assertThrows(NullPointerException.class, () -> {
            reportService.generateReport();
        }, "Storage can't be null");
    }

    @Test
    public void report_ContainTitleIsOk() {
        String[] split = reportService.generateReport().split("\r");
        Assertions.assertEquals(FIRST_LINE,split[0]);
    }
}
