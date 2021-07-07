package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.Test;

public class OutputServiceTest {
    private OutputService reportService;
    private Fruit apple = new Fruit();
    private Fruit banana = new Fruit();

    @Test
    public void test_Report_OK() {
        Storage.storage.put(apple, 10);
        Storage.storage.put(banana, 10);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana," + 10 + System.lineSeparator()
                + "apple," + 10;
        String actual = reportService.makeOutputResult();
        assertEquals(expected, actual);
    }

    @Before
    public void start() {
        reportService = new FruitOutputService();
        Storage.storage.clear();
        apple.setName("apple");
        banana.setName("banana");
    }
}
