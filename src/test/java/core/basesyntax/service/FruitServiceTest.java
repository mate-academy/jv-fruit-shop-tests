package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.FruitServiceImpl;
import org.junit.Before;
import org.junit.Test;

public class FruitServiceTest {
    private FruitService fruitService;

    @Before
    public void init() {
        fruitService = new FruitServiceImpl();
    }

    @Test
    public void fruitService_emptyStorage_ok() {
        Storage.storage.clear();
        String actual = fruitService.getReport();
        String expected = "fruit,quantity";
        assertEquals(expected, actual);
    }

    @Test
    public void fruitService_notEmptyStorage_ok() {
        Fruit fruit = new Fruit("banana");
        int newQuantity = 100;
        Storage.storage.put(fruit, newQuantity);
        String actual = fruitService.getReport();
        String expected = "fruit,quantity" + "\n" + "banana,100";
        assertEquals(expected, actual);
    }
}
