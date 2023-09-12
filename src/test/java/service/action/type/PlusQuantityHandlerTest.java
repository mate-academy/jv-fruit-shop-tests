package service.action.type;

import bd.LocalStorage;
import dao.FruitDaoImpl;
import java.util.ArrayList;
import java.util.List;
import model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.action.ActionStrategyHandler;

public class PlusQuantityHandlerTest {
    private static ActionStrategyHandler handler;
    private static List<Fruit> storage;

    @BeforeClass
    public static void beforeClass() {
        handler = new PlusQuantityHandler(new FruitDaoImpl());
        storage = new ArrayList<>();
    }

    @Test
    public void apply_validWork_Ok() {
        LocalStorage.fruits.add(new Fruit("apple", 100));
        storage.add(new Fruit("apple", 105));
        handler.apply("apple", 5);
        Assert.assertEquals(storage, LocalStorage.fruits);
    }

    @Test
    public void apply_newFruit_Ok() {
        storage.add(new Fruit("banana", 1));
        handler.apply("banana", 1);
        Assert.assertEquals(storage, LocalStorage.fruits);
    }

    @After
    public void tearDown() throws Exception {
        storage.clear();
        LocalStorage.fruits.clear();
    }
}
