package core.basesyntax.service.fruitservice;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import core.basesyntax.service.strategy.operation.BalanceOperationHandlerImpl;
import core.basesyntax.service.strategy.operation.DecreaseOperationHandlerImpl;
import core.basesyntax.service.strategy.operation.IncreaseOperationHandlerImpl;
import core.basesyntax.service.strategy.operation.OperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static List<FruitRecordDto> fruitRecords;
    private static FruitService fruitService;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        fruitRecords = new ArrayList<>();
        fruitService = new FruitServiceImpl();
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitRecordDto.OperationType.BALANCE.getType(),
                new BalanceOperationHandlerImpl());
        operationHandlerMap.put(FruitRecordDto.OperationType.PURCHASE.getType(),
                new DecreaseOperationHandlerImpl());
        operationHandlerMap.put(FruitRecordDto.OperationType.SUPPLY.getType(),
                new IncreaseOperationHandlerImpl());
        operationHandlerMap.put(FruitRecordDto.OperationType.RETURN.getType(),
                new IncreaseOperationHandlerImpl());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void safe_Ok() {
        fruitRecords.add(new FruitRecordDto(new Fruit("apple"),
                100, FruitRecordDto.OperationType.BALANCE));
        fruitRecords.add(new FruitRecordDto(new Fruit("banana"),
                100, FruitRecordDto.OperationType.BALANCE));
        fruitRecords.add(new FruitRecordDto(new Fruit("banana"),
                10, FruitRecordDto.OperationType.RETURN));
        fruitRecords.add(new FruitRecordDto(new Fruit("apple"),
                10, FruitRecordDto.OperationType.RETURN));
        fruitRecords.add(new FruitRecordDto(new Fruit("apple"),
                50, FruitRecordDto.OperationType.SUPPLY));
        fruitRecords.add(new FruitRecordDto(new Fruit("banana"),
                50, FruitRecordDto.OperationType.SUPPLY));
        fruitRecords.add(new FruitRecordDto(new Fruit("apple"),
                10, FruitRecordDto.OperationType.PURCHASE));
        fruitRecords.add(new FruitRecordDto(new Fruit("banana"),
                10, FruitRecordDto.OperationType.PURCHASE));
        fruitService.safe(fruitRecords, operationStrategy);
        Map<Fruit, Integer> actual = Storage.fruitStorage;
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("apple"), 150);
        expected.put(new Fruit("banana"), 150);
        Assert.assertEquals(expected, actual);
    }
}
