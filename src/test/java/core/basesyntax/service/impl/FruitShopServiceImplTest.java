package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.BalanceHandler;
import core.basesyntax.strategy.handler.impl.OperationStrategyImpl;
import core.basesyntax.strategy.handler.impl.PurchaseHandler;
import core.basesyntax.strategy.handler.impl.ReturnHandler;
import core.basesyntax.strategy.handler.impl.SupplyHandler;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitShopServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String ORANGE = "orange";
    private static final String PEAR = "pear";
    private FruitShopService fruitShopService;

    @BeforeEach
      public void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
                = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        fruitShopService = new
              FruitShopServiceImpl(new OperationStrategyImpl(operationHandlerMap));
        Storage.storage.clear();
        Storage.storage.put(APPLE, 10);
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
                new FruitTransaction(FruitTransaction.Operation.BALANCE, ORANGE, 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, 5),
                new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA,10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, PEAR,15)
        );
        fruitShopService.processData(transactionList);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put(APPLE, 5);
        expectedStorage.put(BANANA, 30);
        expectedStorage.put(ORANGE, 100);
        expectedStorage.put(PEAR, 15);
        Assertions.assertEquals(expectedStorage, Storage.storage);
    }

    @Test
    public void test_ProcessData_NotUpdateStorage_NotOk() {
        List<FruitTransaction> transactionList = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, 0),
                new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, 0),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, PEAR,0),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, ORANGE,0)
        );
        fruitShopService.processData(transactionList);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put(APPLE, 10);
        expectedStorage.put(BANANA, 20);
        expectedStorage.put(ORANGE, 0);
        expectedStorage.put(PEAR, 0);
        Assertions.assertEquals(expectedStorage, Storage.storage);
    }
}
