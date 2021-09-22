package core.basesyntax.service.report;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class ReportCreatorImplTest {

    @Test
    public void name() {
        Report report = new ReportCreatorImpl();
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
