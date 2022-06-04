package core.basesyntax.service.implementation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitService;
import core.basesyntax.storage.Storage;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class FruitServiceImplTest {
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final FruitService fruitService = new FruitServiceImpl(fruitDao);

    @After
    public void after() {
        Storage.fruits.clear();
    }

    @Test
    public void add_Ok() {
        Storage.fruits.put("banana", 15);
        fruitService.add("banana", 10);
        int actual = Storage.fruits.get("banana");
        Assert.assertEquals(10, actual);
    }

    @Test(expected = RuntimeException.class)
    public void add_NullFruitName_NotOk() {
        fruitService.add(null, 10);
    }

    @Test(expected = RuntimeException.class)
    public void add_NullAmountValue_NotOk() {
        fruitService.add("banana", null);
    }

    @Test(expected = RuntimeException.class)
    public void add_BlankFruitName_NotOk() {
        fruitService.add(" ", 10);
    }

    @Test(expected = RuntimeException.class)
    public void add_NegativeAmountValue_NotOk() {
        fruitService.add("banana", -2);
    }

    @Test
    public void getQuantity_Ok() {
        Storage.fruits.put("apple", 15);
        int actual = fruitService.getQuantity("apple");
        Assert.assertEquals(15, actual);
    }

    @Test
    public void getQuantity_NonExistingFruit_NotOk() {
        Storage.fruits.put("banana", 15);
        try {
            fruitService.getQuantity("orange");
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test(expected = RuntimeException.class)
    public void getQuantity_NullValue_NotOk() {
        Storage.fruits.put("banana", 15);
        fruitService.getQuantity(null);
    }

    @Test
    public void getQuantity_BlankValue_NotOk() {
        Storage.fruits.put("banana", 16);
        try {
            fruitService.getQuantity("");
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void getAll_Ok() {
        Storage.fruits.put("banana", 10);
        Storage.fruits.put("apple", 5);
        List<Fruit> expected = new ArrayList<>();
        Fruit banana = new Fruit("banana", 10);
        Fruit apple = new Fruit("apple", 5);
        expected.add(banana);
        expected.add(apple);
        List<Fruit> actual = fruitService.getAll();
        Assert.assertEquals(expected, actual);
    }
}
