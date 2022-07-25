package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.Test;

public class ReportServiceImplTest {

    @Test
    public void makeReport_Ok() {
        String header = "fruit,quantity";
        Fruit cherry = new Fruit("cherry");
        Fruit banana = new Fruit("banana");
        Fruit apple = new Fruit("apple");

        Storage.fruits.put(cherry, 10);
        Storage.fruits.put(banana, 20);
        Storage.fruits.put(apple, 30);
        String expected = header + System.lineSeparator()
                + cherry.getName() + "," + 10 + System.lineSeparator()
                + banana.getName() + "," + 20 + System.lineSeparator()
                + apple.getName() + "," + 30;
        String actual = new ReportServiceImpl().makeReport();

        assertEquals("The report was compiled incorrectly", expected, actual);
    }
}
