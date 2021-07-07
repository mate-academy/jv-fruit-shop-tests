package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.StorageTest;
import core.basesyntax.model.Fruit;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitReportServiceTest {
    private static ReportService fruitReportService;

    @BeforeClass
    public static void setFruitReportService() {
        fruitReportService = new FruitReportService(StorageTest.storage);
    }

    @Test
    public void fruitReportService_getReport_ok() {
        StorageTest.storage.put(new Fruit("banana"), 100);
        StorageTest.storage.put(new Fruit("apple"), 110);
        StorageTest.storage.put(new Fruit("pine apple"), 120);
        String actual = fruitReportService.getReport();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,110" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "pine apple,120";
        assertEquals(expected, actual);
    }

}
