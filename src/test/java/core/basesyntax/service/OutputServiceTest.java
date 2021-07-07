package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.Test;

public class OutputServiceTest {
    private OutputService reportService;

    @Test
    public void test_report_ok() {
        reportService = new FruitOutputService();
        Storage.storage.clear();
        Fruit apple = new Fruit();
        Fruit banana = new Fruit();
        apple.setName("apple");
        banana.setName("banana");
        Storage.storage.put(apple, 10);
        Storage.storage.put(banana, 10);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana," + 10 + System.lineSeparator()
                + "apple," + 10;
        String actual = reportService.makeOutputResult();
        assertEquals(expected, actual);
    }
}
