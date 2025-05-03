package service.impl;

import bd.LocalStorage;
import dao.FruitDaoImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Fruit;
import model.Operation;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.UpdateStorageService;
import service.action.ActionStrategyHandler;
import service.action.type.MinusQuantityHandler;
import service.action.type.PlusQuantityHandler;
import service.strategy.ActionStrategyImpl;

public class UpdateStorageServiceImplTest {
    private static UpdateStorageService updateStorageService;
    private static List<Fruit> storage;

    @BeforeClass
    public static void beforeClass() throws Exception {
        HashMap<Operation, ActionStrategyHandler> actionStrategyHashMap = new HashMap<>();
        actionStrategyHashMap.put(Operation.B, new PlusQuantityHandler(new FruitDaoImpl()));
        actionStrategyHashMap.put(Operation.S, new PlusQuantityHandler(new FruitDaoImpl()));
        actionStrategyHashMap.put(Operation.R, new PlusQuantityHandler(new FruitDaoImpl()));
        actionStrategyHashMap.put(Operation.P, new MinusQuantityHandler(new FruitDaoImpl()));
        updateStorageService = new UpdateStorageServiceImpl(
                new ActionStrategyImpl(actionStrategyHashMap),
                new ParserServiceImpl());
        storage = new ArrayList<>();
    }

    @Test
    public void updateStorageData_validWord_ok() {
        storage.add(new Fruit("banana", 20));
        storage.add(new Fruit("apple", 100));
        updateStorageService.updateStorageData(List.of("b,banana,20", "b,apple,100"));
        Assert.assertEquals(storage, LocalStorage.fruits);
    }

    @Test
    public void updateStorageData_differentData_notOk() {
        storage.add(new Fruit("banana", 110));
        storage.add(new Fruit("apple", 100));
        updateStorageService.updateStorageData(List.of("b,banana,20", "b,apple,100"));
        Assert.assertNotEquals(storage, LocalStorage.fruits);
    }

    @After
    public void tearDown() {
        storage.clear();
        LocalStorage.fruits.clear();
    }
}
