package core.basesyntax.service.impl;

import core.basesyntax.service.Report;
import core.basesyntax.storage.Storage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportImplTest {
    private static Report report;

    @BeforeClass
    public static void setUp() {
        report = new ReportImpl();
    }

    @Test
    public void validReport() {
        Storage.fruitStorage.put("banana", 20);
        Storage.fruitStorage.put("apple", 20);
        Storage.fruitStorage.put("tomato", 20);
        String expect = "Fruit, quantity"
                + System.lineSeparator()
                + "banana,20"
                + System.lineSeparator()
                + "apple,20"
                + System.lineSeparator()
                + "tomato,20"
                + System.lineSeparator();
        String actual = report.getReport();
        Assert.assertEquals(expect, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}
