package core.basesyntax.service.impl;

import core.basesyntax.service.ReportCreator;
import core.basesyntax.storage.Storage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static ReportCreator reportCreator;

    @BeforeClass
    public static void setUp() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    public void getReportValidReportIsOk() {
        Storage.fruitStorage.put("banana", 20);
        Storage.fruitStorage.put("orange", 20);
        Storage.fruitStorage.put("apple", 20);
        String expect = "fruit, quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "orange,20" + System.lineSeparator()
                + "apple,20";

        String actual = reportCreator.getReport();
        Assert.assertEquals(expect, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}
