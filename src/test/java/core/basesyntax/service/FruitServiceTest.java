package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceTest {
    private static final Fruit APPLE = new Fruit("apple");
    private static final Fruit BANANA = new Fruit("banana");
    private static final Fruit ORANGE = new Fruit("orange");
    private static final Fruit PINEAPPLE = new Fruit("pineapple");
    private static FruitService fruitService;

    @BeforeClass
    public static void before() {
        fruitService = new FruitServiceImpl();
    }

    @Before
    public void beforeEach() {
        Storage.storage.clear();
    }

    @Test
    public void correctReport_Ok() {
        Storage.storage.put(APPLE, 20);
        Storage.storage.put(BANANA, 10);
        Storage.storage.put(PINEAPPLE, 230);
        Storage.storage.put(ORANGE, 0);

        String expected = "fruit,quantity\n"
                + "banana,10\n"
                + "apple,20\n"
                + "orange,0\n"
                + "pineapple,230";
        String actual = fruitService.getReport();
        assertEquals(expected, actual);
    }

    @Test
    public void emptyStorage_Ok() {
        String expected = "fruit,quantity";
        String actual = fruitService.getReport();
        assertEquals(expected, actual);
    }
}
