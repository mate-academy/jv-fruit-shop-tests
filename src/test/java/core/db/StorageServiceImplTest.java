package core.db;

import core.model.FruitRecord;
import core.model.OperationType;
import core.service.operation.BalanceOperationTypeHandler;
import core.service.operation.OperationTypeHandler;
import core.service.operation.PurchaseOperationTypeHandler;
import core.service.operation.ReturnOperationTypeHandler;
import core.service.operation.SupplyOperationHandler;
import core.service.strategy.OperationTypeStrategy;
import core.service.strategy.OperationTypeStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StorageServiceImplTest {
    public static final Map<String, Integer> stockStorage = new HashMap<>();
    private StorageService storageService;
    private OperationTypeStrategy strategy;

    @Before
    public void setUp() {
        Map<OperationType, OperationTypeHandler> recordMap = new HashMap<>();
        recordMap.put(OperationType.BALANCE, new BalanceOperationTypeHandler());
        recordMap.put(OperationType.PURCHASE, new PurchaseOperationTypeHandler());
        recordMap.put(OperationType.RETURN, new ReturnOperationTypeHandler());
        recordMap.put(OperationType.SUPPLY, new SupplyOperationHandler());
        strategy = new OperationTypeStrategyImpl(recordMap);
        storageService = new StorageServiceImpl();
    }

    @Test
    public void putWithPresentFruitInStorage_Ok() {
        FruitRecord apple = new FruitRecord("apple", 10);
        stockStorage.put("apple", 20);
        stockStorage.put("banana", 100);
        int actual = stockStorage.get("apple") + apple.getAmount();
        int expected = 30;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void putWithPresentFruitInStorage_NotOk() {
        FruitRecord addApple = new FruitRecord("apple", 10);
        stockStorage.put("apple", 20);
        stockStorage.put("banana", 100);
        stockStorage.put("apple", 100);
        int actual = stockStorage.get("apple") + addApple.getAmount();
        int expected = 40;
        Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void putWithNotPresentFruitInStorage_Ok() {
        stockStorage.put("apple", 20);
        int actual = stockStorage.get("apple");
        int expected = 20;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void putWithNotPresentFruitInStorage_NotOk() {
        stockStorage.put("apple", 20);
        int actual = stockStorage.get("apple");
        int expected = 10;
        Assert.assertNotEquals(expected, actual);
    }
}
