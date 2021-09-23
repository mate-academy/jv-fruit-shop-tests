package core.basesyntax.service.report;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static Report report;

    @BeforeClass
    public static void beforeClass() {
        report = new ReportCreatorImpl();
    }

    @Test
    public void reportCreator_Ok() {
        Storage.fruitStorage.put(new Fruit("apple"), 150);
        Storage.fruitStorage.put(new Fruit("banana"), 150);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,150"
                + System.lineSeparator()
                + "apple,150"
                + System.lineSeparator();
        String actual = report.createReport();
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}
