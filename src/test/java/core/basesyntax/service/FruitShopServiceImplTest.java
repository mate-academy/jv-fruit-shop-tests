package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoMapImpl;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.handler.AddToBalance;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.SubtractFromBalance;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static FruitShopService fruitShopService;
    private static FruitShopDao fruitShopDao;
    private static final String APPLE = "apple";
    private static final String ORANGE = "orange";
    private static final String BANANA = "banana";

    @BeforeClass
    public static void setFruitShopService() {
        fruitShopDao = new FruitShopDaoMapImpl();
        OperationHandler operationAddHandler = new AddToBalance(fruitShopDao);
        Map<OperationType, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE, operationAddHandler);
        operationHandlerMap.put(OperationType.SUPPLY, operationAddHandler);
        operationHandlerMap.put(OperationType.RETURN, operationAddHandler);
        operationHandlerMap.put(OperationType.PURCHASE, new SubtractFromBalance(fruitShopDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitShopService = new FruitShopServiceImpl(fruitShopDao, operationStrategy);
    }

    @Test
    public void saveData_isOk() {
        List<FruitRecordDto> data = List.of(
                new FruitRecordDto(OperationType.BALANCE, "apple", 100),
                new FruitRecordDto(OperationType.BALANCE, "orange", 200),
                new FruitRecordDto(OperationType.SUPPLY, "banana", 50));
        fruitShopService.saveData(data);
        Optional<Fruit> apple = fruitShopDao.get(APPLE);
        Optional<Fruit> banana = fruitShopDao.get(BANANA);
        Optional<Fruit> orange = fruitShopDao.get(ORANGE);
        assertEquals(100, apple.get().getAmount());
        assertEquals(200, orange.get().getAmount());
        assertEquals(50, banana.get().getAmount());
    }

    @Test
    public void getReport_isOk() {
        List<FruitRecordDto> data = List.of(
                new FruitRecordDto(OperationType.BALANCE, "orange", 100),
                new FruitRecordDto(OperationType.BALANCE, "apple", 200),
                new FruitRecordDto(OperationType.SUPPLY, "apple", 50));
        fruitShopService.saveData(data);
        String result = fruitShopService.getReport();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "orange,100" + System.lineSeparator()
                + "apple,250";
        assertEquals(expected, result);
    }
}
