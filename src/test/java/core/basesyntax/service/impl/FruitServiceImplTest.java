package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Test;

public class FruitServiceImplTest {
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final FruitService fruitService = new FruitServiceImpl(fruitDao);

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void update_Ok() {
        Storage.fruits.put("apple", 15);
        fruitService.update("apple", 18);
        int actualResult = Storage.fruits.get("apple");
        assertEquals(18, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void update_nullFruit_NotOk() {
        Storage.fruits.put("apple", 15);
        fruitService.update(null, 23);
    }

    @Test(expected = RuntimeException.class)
    public void update_AmountNull_NotOk() {
        Storage.fruits.put("apple", 15);
        fruitService.update("apple", null);
    }

    @Test(expected = RuntimeException.class)
    public void update_FruitAndAmountNull_NotOk() {
        Storage.fruits.put("apple", 15);
        fruitService.update(null, null);
    }

    @Test(expected = RuntimeException.class)
    public void update_NegativeAmount_NotOk() {
        fruitService.update("apple", -15);
    }

    @Test
    public void getAll_ok() {
        Storage.fruits.put("banana", 67);
        Storage.fruits.put("apple", 17);
        List<Fruit> expectedList = new ArrayList<>();
        Fruit banana = new Fruit("banana", 67);
        Fruit apple = new Fruit("apple", 17);
        expectedList.add(banana);
        expectedList.add(apple);
        List<Fruit> actualList = fruitService.getAll();
        assertEquals(expectedList,actualList);
    }

    @Test
    public void getQuantity_Existing_Ok() {
        Storage.fruits.put("banana", 67);
        int actual = fruitService.getQuantity("banana");
        assertEquals(67, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getQuantity_NonExisting_NotOk() {
        fruitService.getQuantity("apple");
    }

    @Test(expected = RuntimeException.class)
    public void getQuantityNull_notOk() {
        Storage.fruits.put("banana", null);
        int actual = fruitService.getQuantity("banana");
        assertEquals(67, actual);
    }
}
