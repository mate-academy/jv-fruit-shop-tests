package core.basesyntax.service.impl;

import core.basesyntax.Main;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.FruitStrategy;
import core.basesyntax.service.OperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static Map<String, OperationHandler> handlerMap;
    private static FruitStrategy fruitStrategy;
    private static FruitService fruitService;
    private static List<FruitRecordDto> dtos;

    @BeforeClass
    public static void beforeClass() throws Exception {
        handlerMap = new HashMap<>();
        handlerMap.put(Main.BALANCE, new BalanceOperationHandler());
        handlerMap.put(Main.SUPPLY, new AddOperationHandler());
        handlerMap.put(Main.RETURN, new AddOperationHandler());
        handlerMap.put(Main.PURCHASE, new SubtractOperationHandler());
        fruitStrategy = new FruitStrategyImpl(handlerMap);
        fruitService = new FruitServiceImpl(fruitStrategy);
        dtos = new ArrayList<>();
    }

    @Before
    public void setUp() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void saveDto_isOk() {
        Map<Fruit, Integer> expectedStorage = new HashMap<>();
        Fruit apple = new Fruit("apple");
        Fruit banana = new Fruit("banana");
        expectedStorage.put(apple, 100);
        expectedStorage.put(banana, 200);
        dtos.add(new FruitRecordDto(Main.BALANCE, "apple", 100));
        dtos.add(new FruitRecordDto(Main.BALANCE, "banana", 100));
        dtos.add(new FruitRecordDto(Main.RETURN,"banana",100));
        dtos.add(new FruitRecordDto(Main.PURCHASE, "apple", 50));
        dtos.add(new FruitRecordDto(Main.SUPPLY, "apple", 50));
        fruitService.saveDto(dtos);
        Assert.assertEquals(expectedStorage.get(apple), Storage.fruits.get(apple));
        Assert.assertEquals(expectedStorage.get(banana), Storage.fruits.get(banana));
    }
}
