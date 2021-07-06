package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.FruitReportService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitReportServiceTest {
    private static ReportService fruitReportService;

    @BeforeClass
    public static void beforeClass() {
        Storage.getStorage().put(new Fruit("apple"), 10);
        Storage.getStorage().put(new Fruit("banana"), 20);
        fruitReportService = new FruitReportService();
    }

    @Test
    public void report_createReport_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,10";
        Assert.assertEquals(expected, fruitReportService.getReport());
    }

    @AfterClass
    public static void afterClass() {
        Storage.getStorage().clear();
    }
}
