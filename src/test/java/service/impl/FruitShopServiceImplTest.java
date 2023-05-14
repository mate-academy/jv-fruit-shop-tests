package service.impl;

import static org.junit.Assert.assertEquals;

import db.Storage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.FruitShopService;
import strategy.OperationHandler;
import strategy.OperationStrategy;
import strategy.impl.BalanceOperationHandler;
import strategy.impl.OperationStrategyImpl;
import strategy.impl.PurchaseOperationHandler;
import strategy.impl.ReturnOperationHandler;
import strategy.impl.SupplyOperationHandler;

public class FruitShopServiceImplTest {
    public static final int APPLE_QUANTITY = 120;
    public static final int BANANA_QUANTITY = 130;
    public static final int MANGO_QUANTITY = 100;
    public static final int KIWI_QUANTITY = 80;
    public static final String BANANA = "banana";
    public static final String APPLE = "apple";
    public static final String MANGO = "mango";
    public static final String KIWI = "kiwi";
    private static Map<FruitTransaction.Operation, OperationHandler>
            operationHandlerMap = new HashMap<>();
    private static OperationStrategy operationStrategy;
    private static FruitShopService fruitShopService;

    @BeforeClass
    public static void beforeAll() {
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);

    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void calculate_validData_ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "kiwi", 100),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "mango", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "kiwi", 20));
        fruitShopService.calculate(expected);
        int actualMango = Storage.fruits.get(MANGO);
        Assert.assertEquals(
                "FruitShopService failed",
                MANGO_QUANTITY, actualMango);
        int actualApple = Storage.fruits.get(APPLE);
        Assert.assertEquals(
                "FruitShopService failed",
                APPLE_QUANTITY, actualApple);
        int actualBanana = Storage.fruits.get(BANANA);
        Assert.assertEquals(
                "FruitShopService failed",
                BANANA_QUANTITY, actualBanana);
        int actualKiwi = Storage.fruits.get(KIWI);
        Assert.assertEquals(
                "FruitShopService failed",
                KIWI_QUANTITY, actualKiwi);
    }

    @Test
    public void calculate_emptyList_ok() {
        List<FruitTransaction> emptyList = new ArrayList<>();
        fruitShopService.calculate(emptyList);
        int expected = 0;
        int actual = Storage.fruits.size();
        assertEquals(expected, actual);
    }
}
