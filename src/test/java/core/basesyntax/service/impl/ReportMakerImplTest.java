package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportMaker;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMakerImplTest {
    private static ReportMaker report;

    @BeforeClass
    public static void setup() {
        report = new ReportMakerImpl();
        Storage.store.put(new Fruit("apple"), 12);
        Storage.store.put(new Fruit("orange"), 9);
    }

    @Test
    public void createReport_correct_ok() {
        String expected = "fruit, quantity" + "\n" + "apple, 12" + "\n" + "orange, 9" + "\n";
        String actual = report.createNewReport();
        Assert.assertEquals(actual, expected);
    }
}
