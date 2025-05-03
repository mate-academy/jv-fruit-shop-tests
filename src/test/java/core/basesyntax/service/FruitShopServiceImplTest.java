package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitOperationDto;
import core.basesyntax.model.OperationType;
import core.basesyntax.operations.BalanceHandler;
import core.basesyntax.operations.ObtainingHandler;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.operations.OperationStrategy;
import core.basesyntax.operations.OperationStrategyImpl;
import core.basesyntax.operations.PurchaseHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static Map<String, OperationHandler> operationHandlerMap;
    private static OperationStrategy operationStrategy;
    private static FruitShopService fruitShopService;
    private static List<FruitOperationDto> fruitOperationDtoList;
    private static List<FruitOperationDto> invalidFruitOperationDtoList;

    @BeforeClass
    public static void beforeClass() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE.getShortOperation(),
                new BalanceHandler());
        operationHandlerMap.put(OperationType.PURCHASE.getShortOperation(),
                new PurchaseHandler());
        operationHandlerMap.put(OperationType.RETURN.getShortOperation(),
                new ObtainingHandler());
        operationHandlerMap.put(OperationType.SUPPLY.getShortOperation(),
                new ObtainingHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
        fruitOperationDtoList = new ArrayList<>();
        fruitOperationDtoList.add(new FruitOperationDto(OperationType.BALANCE,
                new Fruit("banana"), 20));
        fruitOperationDtoList.add(new FruitOperationDto(OperationType.RETURN,
                new Fruit("banana"), 30));
        fruitOperationDtoList.add(new FruitOperationDto(OperationType.SUPPLY,
                new Fruit("banana"), 50));
        fruitOperationDtoList.add(new FruitOperationDto(OperationType.PURCHASE,
                new Fruit("banana"), 90));
        fruitOperationDtoList.add(new FruitOperationDto(OperationType.BALANCE,
                new Fruit("apple"), 40));
        invalidFruitOperationDtoList = new ArrayList<>();
        invalidFruitOperationDtoList.add(new FruitOperationDto(OperationType.PURCHASE,
                new Fruit("banana"), 20));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void saveToStorage_ValidList_Ok() {
        fruitShopService.saveToStorage(fruitOperationDtoList);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 10);
        expected.put(new Fruit("apple"), 40);
        assertEquals(expected, Storage.storage);
    }

    @Test(expected = RuntimeException.class)
    public void saveToStorage_InvalidList_Ok() {
        fruitShopService.saveToStorage(invalidFruitOperationDtoList);
    }

    @Test
    public void createReport_Ok() {
        Storage.storage.put(new Fruit("banana"), 30);
        Storage.storage.put(new Fruit("apple"), 100);
        String actual = fruitShopService.createReport();
        String expected = "fruit, quantity" + System.lineSeparator()
                + "banana,30" + System.lineSeparator() + "apple,100"
                + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_WithEmptyStorage_Ok() {
        String actual = fruitShopService.createReport();
        String expected = "fruit, quantity" + System.lineSeparator();
        assertEquals(expected, actual);
    }
}
