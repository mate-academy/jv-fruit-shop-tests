package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.servise.ReportService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        assertTrue("Storage is not Empty",Storage.storageMap.size() > 0);
    }

    @Test
    public void report_Storage_IsEmptyIsNotOk() {
        Storage.storageMap.clear();
        assertTrue("Storage is Empty",Storage.storageMap.isEmpty());
    }

    @Test
    public void report_Storage_IsNull() {
        Storage.storageMap.clear();
        Storage.storageMap.put(null,null);
        Assert.assertThrows("Storage can't be null",NullPointerException.class, () -> {
            reportService.generateReport();
        });
    }

    @Test
    public void report_ContainTitleIsOk() {
        String[] split = reportService.generateReport().split("\r");
        assertEquals(FIRST_LINE,split[0]);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.storageMap.clear();
    }
}
