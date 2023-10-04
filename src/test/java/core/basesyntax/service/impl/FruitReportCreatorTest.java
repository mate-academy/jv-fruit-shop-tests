package core.basesyntax.service.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitReportCreatorTest {

    private static Storage storage;

    @BeforeAll
    public static void setUp() {
        storage = new FruitStorage();
    }

    @Test
    public void getReport_emptyStorage_Ok() {
        ReportCreator report = new FruitReportCreator(storage);
        String expected = "fruit, quantity\n";
        String actual = report.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getReport_storageWithData_Ok() {
        storage.addFruitInQuantity("banana",20);
        storage.addFruitInQuantity("orange",12);
        storage.addFruitInQuantity("pineapple", 3);
        String expected = "fruit, quantity\nbanana,20\norange,12\npineapple,3\n";
        ReportCreator report = new FruitReportCreator(storage);
        String actual = report.createReport();
        Assert.assertEquals(expected, actual);
    }

    @AfterEach
    public void clean() {
        storage.removeAllItems();
    }
}
