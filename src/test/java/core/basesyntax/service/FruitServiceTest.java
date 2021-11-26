package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.fruitservice.FruitService;
import core.basesyntax.service.fruitservice.FruitServiceImpl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceTest {
    private static FruitService fruitService;

    @BeforeClass
    public static void beforeClass() {
        fruitService = new FruitServiceImpl();
    }

    @After
    public void after() {
        Storage.storage.clear();
    }

    @Test
    public void fruitService_emptyStorage_ok() {
        String actual = fruitService.createReport();
        String expected = "fruit,quantity";
        assertEquals(expected, actual);
    }

    @Test
    public void fruitService_notEmptyStorage_ok() {
        Storage.storage.put(new Fruit("banana"), 100);
        String actual = fruitService.createReport();
        String expected = "fruit,quantity" + System.lineSeparator() + "banana,100";
        assertEquals(expected, actual);
    }
}
