package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceTest {
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
        Storage.storage.put(new Fruit("apple"), 20);
        Storage.storage.put(new Fruit("banana"), 10);
        Storage.storage.put(new Fruit("pineapple"), 230);
        Storage.storage.put(new Fruit("fruit"), 0);

        String expected = "fruit,quantity\napple,20\nbanana,10\npineapple,230\nfruit,0";
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
