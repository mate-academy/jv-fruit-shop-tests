package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
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
    public void testGetReport() {
        Storage.storage.put(APPLE, 20);
        Storage.storage.put(BANANA, 10);
        Storage.storage.put(PINEAPPLE, 230);
        Storage.storage.put(ORANGE, 0);

        String expected = "fruit,quantity\napple,20\nbanana,10\npineapple,230\norange,0";
        List<String> expectedSplited = List.of(expected.split("\n"));
        String actual = fruitService.getReport();
        List<String> actualSplited = List.of(actual.split("\n"));
        assertTrue(actualSplited.size() == expectedSplited.size()
                && actualSplited.containsAll(expectedSplited));
    }

    @Test
    public void testGetReport_EmptyStorage() {
        String expected = "fruit,quantity\n";
        List<String> expectedSplited = List.of(expected.split("\n"));
        String actual = fruitService.getReport();
        List<String> actualSplited = List.of(actual.split("\n"));
        assertTrue(actualSplited.size() == expectedSplited.size()
                && actualSplited.containsAll(expectedSplited));
    }
}
