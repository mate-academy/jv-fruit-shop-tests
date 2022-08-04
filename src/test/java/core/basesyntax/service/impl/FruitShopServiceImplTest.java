package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static final int APPLES_AMOUNT = 90;
    private static final int BANANAS_AMOUNT = 68;
    private static FruitShopService shopService;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
                = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitDao));

        OperationStrategy strategy =
                new OperationStrategyImpl(operationHandlerMap);
        shopService = new FruitShopServiceImpl(strategy);
        List<FruitTransaction> transaction = new ArrayList<>();
        transaction.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 100));
        transaction.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 50));
        transaction.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 20));
        transaction.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 18));
        transaction.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple", 10));
        shopService.process(transaction);
    }

    @Test
    public void process_calculateValues_ok() {
        int expectedStorageSize = 2;
        int actualStorageSize = Storage.fruits.size();
        assertEquals("Test fail! Expected storage size: " + expectedStorageSize
                + ", actual size: " + actualStorageSize, expectedStorageSize, actualStorageSize);

        int expectedAmountOfApples = APPLES_AMOUNT;
        int expectedAmountOfBananas = BANANAS_AMOUNT;
        Fruit apple = Storage.fruits.stream()
                .filter(f -> f.getName().equals("apple")).findFirst().get();
        Fruit banana = Storage.fruits.stream()
                .filter(f -> f.getName().equals("banana")).findFirst().get();
        int actualAmountOfApples = apple.getAmount();
        int actualAmountOfBananas = banana.getAmount();
        assertEquals("Test fail! Expected amount of apples: " + expectedAmountOfApples
                + ", actual amount: " + actualAmountOfApples,
                expectedAmountOfApples, actualAmountOfApples);
        assertEquals("Test fail! Expected amount of bananas: " + expectedAmountOfBananas
                + ", actual amount: " + actualAmountOfBananas,
                expectedAmountOfBananas, actualAmountOfBananas);
    }

    @Test
    public void getAllData_correctConvert_Ok() {
        Map<String, Integer> actualMap = shopService.getAllData();
        int expectedMapSize = 2;
        int actualMapSize = actualMap.size();
        assertEquals("Test fail! Expected map size: " + expectedMapSize
                + ", actual size: " + actualMapSize, expectedMapSize, actualMapSize);

        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("apple", APPLES_AMOUNT);
        expectedMap.put("banana", BANANAS_AMOUNT);
        assertEquals("Test fail! Map not equals, expected map: "
                + expectedMap + ", actual map :" + actualMap,expectedMap, actualMap);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
