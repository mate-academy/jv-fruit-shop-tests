package service.impl;

import static org.junit.Assert.assertEquals;

import db.Storage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Fruit;
import model.OperationType;
import model.dto.FruitRecordDto;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import service.FruitService;
import service.FruitStrategy;
import service.OperationHandler;

public class FruitServiceImplTest {
    private static FruitService fruitService;
    private static FruitStrategy fruitStrategy;
    private static final Map<OperationType, OperationHandler> fruitOperationsServiceMap
            = new HashMap<>();

    @BeforeClass
    public static void servicesInit() {
        fruitOperationsServiceMap.put(OperationType.BALANCE, new BalanceOperation());
        fruitOperationsServiceMap.put(OperationType.SUPPLY, new AddOperation());
        fruitOperationsServiceMap.put(OperationType.PURCHASE, new RemoveOperation());
        fruitOperationsServiceMap.put(OperationType.RETURN, new ReturnOperation());
        fruitStrategy = new FruitStrategyImpl(fruitOperationsServiceMap);
        fruitService = new FruitServiceImpl(fruitStrategy);
    }

    @Before
    public void clearStorage() {
        Storage.fruits.clear();
    }

    @Test
    public void saveDto_Ok() {
        int expectedBananas = 120;
        int expectedApples = 60;
        List<FruitRecordDto> dtos = List.of(
                new FruitRecordDto(OperationType.BALANCE, "banana", 20),
                new FruitRecordDto(OperationType.BALANCE, "apple", 100),
                new FruitRecordDto(OperationType.SUPPLY, "banana", 100),
                new FruitRecordDto(OperationType.PURCHASE, "apple", 50),
                new FruitRecordDto(OperationType.RETURN, "apple", 10));
        fruitService.saveDto(dtos);
        int actualBananas = Storage.fruits.get(new Fruit("banana"));
        int actualApples = Storage.fruits.get(new Fruit("apple"));
        assertEquals(expectedBananas, actualBananas);
        assertEquals(expectedApples, actualApples);
    }
}
