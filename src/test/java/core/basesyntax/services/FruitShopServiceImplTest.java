package core.basesyntax.services;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FruitShopServiceImplTest {
    private static final String PINEAPPLE = "pineapple";
    private static final String BANANA = "banana";
    private static final String ORANGE = "orange";
    private static final String DRAGON_FRUIT = "dragonFruit";
    private FruitShopService fruitShopService;

    @BeforeEach
    public void setUp() {
        initFruitShopService();
        initStorage();

    }

    private void initFruitShopService() {
        Map<Operation, OperationHandler> operationHandlerMap
                = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyHandler());
        fruitShopService = new FruitShopServiceImpl(new OperationStrategyImpl(operationHandlerMap));
    }

    private void initStorage() {
        Storage.storage.clear();
        Storage.storage.put(PINEAPPLE, 10);
        Storage.storage.put(BANANA, 20);
        Storage.storage.put(ORANGE, 30);
    }

    @AfterEach
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void test_ProcessData_UpdateStorage_Ok() {
        List<FruitTransaction> transactionList = Arrays.asList(
                new FruitTransaction(Operation.BALANCE, ORANGE, 100),
                new FruitTransaction(Operation.PURCHASE, PINEAPPLE, 5),
                new FruitTransaction(Operation.RETURN, BANANA, 10),
                new FruitTransaction(Operation.SUPPLY, DRAGON_FRUIT, 15)
        );
        fruitShopService.processData(transactionList);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put(PINEAPPLE, 5);
        expectedStorage.put(BANANA, 30);
        expectedStorage.put(ORANGE, 100);
        expectedStorage.put(DRAGON_FRUIT, 15);
        Assertions.assertEquals(expectedStorage, Storage.storage);
    }

    @Test
    public void test_ProcessData_NotUpdateStorage_notOk() {
        List<FruitTransaction> transactionList = Arrays.asList(
                new FruitTransaction(Operation.PURCHASE, PINEAPPLE, 0),
                new FruitTransaction(Operation.RETURN, BANANA, 0),
                new FruitTransaction(Operation.SUPPLY, DRAGON_FRUIT, 0),
                new FruitTransaction(Operation.BALANCE, ORANGE, 0)
        );
        fruitShopService.processData(transactionList);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put(PINEAPPLE, 10);
        expectedStorage.put(BANANA, 20);
        expectedStorage.put(ORANGE, 0);
        expectedStorage.put(DRAGON_FRUIT, 0);
        Assertions.assertEquals(expectedStorage, Storage.storage);
    }
}