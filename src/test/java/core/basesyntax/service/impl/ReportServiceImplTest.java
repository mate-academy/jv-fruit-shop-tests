package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void getReport_StorageNotEmpty_ok() {
        Storage.fruitStorage.put("banana",20);
        Storage.fruitStorage.put("apple",30);
        StringBuilder expected = new StringBuilder("fruit,quantity");
        expected.append(System.lineSeparator()).append("banana,20")
            .append(System.lineSeparator()).append("apple,30");
        String actual = reportService.getReport(Storage.fruitStorage);
        Assert.assertEquals("Test failed! Expected: "+ System.lineSeparator() + expected
                        + System.lineSeparator() + " but actual is " + System.lineSeparator() + actual,
                expected.toString(), actual);
    }

    @Test
    public void getReport_EmptyStorage_ok() {
        String expected = "fruit,quantity" + System.lineSeparator() + "Storage is empty";
        String actual = reportService.getReport(Storage.fruitStorage);
        Assert.assertEquals("Test failed! Expected: "+ System.lineSeparator() + expected
                + System.lineSeparator() + " but actual is " + System.lineSeparator() + actual,
                expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getReport_nullValue_notOk() {
        reportService.getReport(null);
    }
}