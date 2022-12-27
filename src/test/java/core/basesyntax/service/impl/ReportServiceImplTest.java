package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.servise.ReportService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String FIRST_LINE = "fruit, quantity";
    private static ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
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
        assertThrows("Storage can't be null",NullPointerException.class, () -> {
            reportService.generateReport();
        });
    }

    @Test
    public void name() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,quantity").append(System.lineSeparator())
                .append("avocado,40").append(System.lineSeparator())
                .append("banana,43").append(System.lineSeparator())
                .append("apple,185").append(System.lineSeparator());
        Storage.storageMap.put(new Fruit("avocado"),40);
        Storage.storageMap.put(new Fruit("banana"),43);
        Storage.storageMap.put(new Fruit("apple"),185);
        assertEquals(stringBuilder.toString(), reportService.generateReport());
    }

    @After
    public void tearDown() {
        Storage.storageMap.clear();
    }
}
