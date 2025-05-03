package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static ReportCreator reportCreator;

    @BeforeClass
    public static void beforeClass() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    public void reportCreator_valid_Ok() {
        Storage.storage.put("banana", 15);
        Storage.storage.put("apple", 20);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,15"
                + System.lineSeparator()
                + "apple,20";
        String actual = reportCreator.createReport();
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storage.clear();
    }
}
