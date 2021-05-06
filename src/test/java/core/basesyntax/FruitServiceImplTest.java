package core.basesyntax;

import core.basesyntax.database.Storage;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.handler.DecreaseOperationHandler;
import core.basesyntax.handler.IncreaseOperationHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.implementions.FruitServiceImpl;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitService fruitService;

    @BeforeClass
    public static void beforeClass() {
        Map<OperationType, OperationHandler> operationStrategyMap = new HashMap<>();
        OperationHandler increaseHandler = new IncreaseOperationHandler();

        operationStrategyMap.put(OperationType.BALANCE, increaseHandler);
        operationStrategyMap.put(OperationType.RETURN, increaseHandler);
        operationStrategyMap.put(OperationType.SUPPLY, increaseHandler);
        operationStrategyMap.put(OperationType.PURCHASE, new DecreaseOperationHandler());

        fruitService = new FruitServiceImpl(new OperationStrategyImpl(operationStrategyMap),
                                            new FruitRecordDto());
    }

    @Test
    public void saveData_chooseCorrectStrategy_isOk() {
        Fruit apple = new Fruit("apple");
        Fruit banana = new Fruit("banana");
        List<FruitRecordDto> recordDtos = List.of(
                new FruitRecordDto(apple, 20, OperationType.BALANCE),
                new FruitRecordDto(banana, 30, OperationType.BALANCE),
                new FruitRecordDto(apple, 5, OperationType.RETURN),
                new FruitRecordDto(banana, 30, OperationType.PURCHASE));
        fruitService.saveData(recordDtos);

        Optional<Integer> optionalAppleQuantity = Optional.of(Storage.fruitStorage.get(apple));
        Optional<Integer> optionalBananaQuantity = Optional.of(Storage.fruitStorage.get(banana));
        int actualAppleQuantity = optionalAppleQuantity.orElse(0);
        int actualBananaQuantity = optionalBananaQuantity.orElse(0);
        Assert.assertEquals(25, actualAppleQuantity);
        Assert.assertEquals(0, actualBananaQuantity);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test (expected = RuntimeException.class)
    public void saveData_chooseCorrectStrategy_NotOk() {
        Fruit banana = new Fruit("banana");
        List<FruitRecordDto> recordDtos = List.of(
                new FruitRecordDto(banana, 20, OperationType.BALANCE),
                new FruitRecordDto(banana, 5, OperationType.RETURN),
                new FruitRecordDto(banana, 30, OperationType.PURCHASE));
        fruitService.saveData(recordDtos);
    }
}
