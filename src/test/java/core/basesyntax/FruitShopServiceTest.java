package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Storage;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.AdditionStrategy;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.FruitShopServiceImpl;
import core.basesyntax.service.Operation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ReduceStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceTest {
    private static List<FruitRecordDto> expectedTransactionDtos;
    private static FruitShopService fruitService;
    private static final AdditionStrategy additionStrategy = new AdditionStrategy();

    @After
    public void clear() {
        Storage.fruits.clear();
    }

    @BeforeClass
    public static void setUp() {
        Map<Operation, OperationStrategy> operationStrategyMap = new HashMap<>();
        operationStrategyMap.put(Operation.BALANCE, additionStrategy);
        operationStrategyMap.put(Operation.SUPPLY, additionStrategy);
        operationStrategyMap.put(Operation.RETURN, additionStrategy);
        operationStrategyMap.put(Operation.PURCHASE, new ReduceStrategy());
        fillTransactionDto();
        fruitService = new FruitShopServiceImpl(operationStrategyMap);
    }

    @Test
    public void applyOperationOnFruitsDto() {
        Fruit apple = new Fruit("apple");
        Fruit banana = new Fruit("banana");
        fruitService.applyOperationOnFruitsDto(expectedTransactionDtos);
        Assert.assertEquals(90, Storage.fruits.get(apple).intValue());
        Assert.assertEquals(152, Storage.fruits.get(banana).intValue());
    }

    @Test
    public void getReport_ok() {
        Storage.fruits.put(new Fruit("orange"), 50);
        Storage.fruits.put(new Fruit("cherry"), 20);
        Map<Fruit, Integer> actual = fruitService.getFruits();
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("orange"), 50);
        expected.put(new Fruit("cherry"), 20);
        Assert.assertEquals(expected, actual);
    }

    private static void fillTransactionDto() {
        expectedTransactionDtos = new ArrayList<>();
        expectedTransactionDtos.add(new FruitRecordDto(Operation.BALANCE,
                new Fruit("banana"), 20));
        expectedTransactionDtos.add(new FruitRecordDto(Operation.BALANCE,
                new Fruit("apple"), 100));
        expectedTransactionDtos.add(new FruitRecordDto(Operation.SUPPLY,
                new Fruit("banana"), 100));
        expectedTransactionDtos.add(new FruitRecordDto(Operation.PURCHASE,
                new Fruit("banana"), 13));
        expectedTransactionDtos.add(new FruitRecordDto(Operation.RETURN,
                new Fruit("apple"), 10));
        expectedTransactionDtos.add(new FruitRecordDto(Operation.PURCHASE,
                new Fruit("apple"), 20));
        expectedTransactionDtos.add(new FruitRecordDto(Operation.PURCHASE,
                new Fruit("banana"), 5));
        expectedTransactionDtos.add(new FruitRecordDto(Operation.SUPPLY,
                new Fruit("banana"), 50));
    }
}
